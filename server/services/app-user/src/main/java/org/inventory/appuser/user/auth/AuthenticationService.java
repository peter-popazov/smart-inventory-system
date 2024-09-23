package org.inventory.appuser.user.auth;

import lombok.RequiredArgsConstructor;
import org.inventory.appuser.user.exception.TokenExpiredException;
import org.inventory.appuser.user.exception.TokenNotFoundException;
import org.inventory.appuser.user.exception.UserNotFoundException;
import org.inventory.appuser.user.helpers.AuthUserRequest;
import org.inventory.appuser.user.helpers.AuthenticationResponse;
import org.inventory.appuser.user.helpers.RegisterUserRequest;
import org.inventory.appuser.user.model.AppUser;
import org.inventory.appuser.user.model.Role;
import org.inventory.appuser.user.model.Token;
import org.inventory.appuser.user.repos.AppUserRepository;
import org.inventory.appuser.user.repos.RoleRepository;
import org.inventory.appuser.user.repos.TokenRepository;
import org.inventory.appuser.user.security.JWTService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Value("${application.security.activationTokenLen}")
    private Integer activationTokenLength;

    @Value("${application.security.tokenExpirationTime}")
    private Integer tokenExpirationTime;

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public Integer register(RegisterUserRequest request) {
        Role userRole = roleRepository.findByName(request.role())
                .orElseGet(() -> roleRepository.save(
                        Role.builder()
                                .name(request.role())
                                .build()
                ));

        AppUser appUser = AppUser.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(userRole)
                .accountLocked(false)
                .accountEnabled(false)
                .build();

        Integer savedUserId = appUserRepository.save(appUser).getUserId();

        userRole.setAppUser(appUser);
        roleRepository.save(userRole);

        sendValidationEmail(appUser);
        return savedUserId;
    }

    private void sendValidationEmail(AppUser appUser) {
        String token = generateAndSaveActivationToken(appUser);
        // todo send email -> kafka -> notification microservice (2.33)
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

    public AuthenticationResponse authenticate(AuthUserRequest request) {
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

    public Boolean activateAccount(String token) {
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
        return true;
    }
}
