package org.peter.auth.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.peter.auth.ServerResponse;
import org.peter.auth.exception.TokenExpiredException;
import org.peter.auth.exception.TokenNotFoundException;
import org.peter.auth.exception.UserNotFoundException;
import org.peter.auth.helpers.AuthUserRequest;
import org.peter.auth.helpers.AuthenticationResponse;
import org.peter.auth.helpers.RegisterUserRequest;
import org.peter.auth.kafka.Email;
import org.peter.auth.kafka.EmailProducer;
import org.peter.auth.model.AppUser;
import org.peter.auth.model.Token;
import org.peter.auth.repository.AppUserRepository;
import org.peter.auth.repository.TokenRepository;
import org.peter.auth.security.JWTService;
import org.peter.auth.security.UserDetailsServiceIml;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Value("${app.security.activationTokenLen}")
    private Integer activationTokenLength;

    @Value("${app.security.tokenExpirationTime}")
    private Integer tokenExpirationTime;

    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserDetailsServiceIml userDetailsService;
    private final EmailProducer emailProducer;

    public ServerResponse<Integer> register(@Valid RegisterUserRequest request) {
        AppUser appUser = AppUser.builder()
                .firstName(capitalizeFirstLetter(request.firstName()))
                .lastName(capitalizeFirstLetter(request.lastName()))
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .accountLocked(false)
                .accountEnabled(false)
                .build();

        Integer savedUserId = appUserRepository.save(appUser).getUserId();

        sendValidationEmail(appUser);
        return ServerResponse.<Integer>builder().response(savedUserId).build();
    }

    private void sendValidationEmail(AppUser appUser) {
        String token = generateAndSaveActivationToken(appUser);
        emailProducer.sendEmail(Email.builder()
                .userEmail(appUser.getEmail())
                .payload(token)
                .build());
        System.out.println("____________________" + token);
    }

    private String generateAndSaveActivationToken(AppUser appUser) {
        String generatedToken = generateActivationToken(activationTokenLength);
        Token token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(tokenExpirationTime))
                .appUser(appUser)
                .build();

        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationToken(int length) {
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }

        return sb.toString();
    }

    public AuthenticationResponse authenticate(@Valid AuthUserRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        var claims = new HashMap<String, Object>();
        AppUser appUser = (AppUser) auth.getPrincipal();
        claims.put("email", appUser.getEmail());
        var jwt = jwtService.generateToken(claims, appUser);

        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }

    public ServerResponse<Boolean> activateAccount(String token) {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenNotFoundException("Token not found"));

        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            // todo send email
            throw new TokenExpiredException("Token has expired. New token has been sent");
        }

        AppUser appUser = appUserRepository.findById(savedToken.getAppUser().getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        appUser.setAccountEnabled(true);
        appUserRepository.save(appUser);

        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);

        return ServerResponse.<Boolean>builder().response(true).build();
    }

    private String capitalizeFirstLetter(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }

        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public Boolean validateToken(String token, String email) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return jwtService.isTokenValid(token, userDetails);
    }
}
