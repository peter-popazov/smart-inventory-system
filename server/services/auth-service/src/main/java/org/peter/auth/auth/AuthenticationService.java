package org.peter.auth.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.peter.auth.ServerResponse;
import org.peter.auth.client.CustomerClient;
import org.peter.auth.exception.TokenExpiredException;
import org.peter.auth.exception.TokenNotFoundException;
import org.peter.auth.exception.UserNotFoundException;
import org.peter.auth.dto.AuthUserRequest;
import org.peter.auth.dto.AuthenticationResponse;
import org.peter.auth.dto.RegisterRequest;
import org.peter.auth.dto.UserResponse;
import org.peter.auth.kafka.Email;
import org.peter.auth.kafka.EmailProducer;
import org.peter.auth.kafka.RegisteredProducer;
import org.peter.auth.kafka.RegisteredRequest;
import org.peter.auth.model.AppUser;
import org.peter.auth.model.RoleType;
import org.peter.auth.model.Token;
import org.peter.auth.repository.AppUserRepository;
import org.peter.auth.repository.TokenRepository;
import org.peter.auth.security.JWTService;
import org.peter.auth.security.UserDetailsServiceIml;
import org.peter.auth.client.UserClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;

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
    private final RegisteredProducer registeredProducer;
    private final UserClient userClient;
    private final CustomerClient customerClient;

    public ServerResponse<Integer> register(@Valid RegisterRequest request) {
        AppUser appUser = AppUser.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .accountLocked(false)
                .accountEnabled(false)
                .role(request.registeringPerson())
                .build();

        AppUser appUserRegistered = appUserRepository.save(appUser);

        sendValidationEmail(appUserRegistered);

        if (request.registeringPerson() == RoleType.USER) {
            userRegisteredMessage(appUserRegistered.getEmail(), appUserRegistered.getUserId());
        } else if (request.registeringPerson() == RoleType.CUSTOMER) {
            customerRegisteredMessage(appUserRegistered.getEmail(), appUserRegistered.getUserId());
        }

        return ServerResponse.<Integer>builder().response(appUserRegistered.getUserId()).build();
    }

    private void sendValidationEmail(AppUser appUser) {
        String token = generateAndSaveActivationToken(appUser);
        emailProducer.sendEmail(Email.builder()
                .userEmail(appUser.getEmail())
                .payload(token)
                .build());
    }

    private void userRegisteredMessage(String userEmail, Integer userId) {
        registeredProducer.userRegistered(
                RegisteredRequest.builder()
                        .userEmail(userEmail)
                        .userId(userId)
                        .build()
        );
    }

    private void customerRegisteredMessage(String userEmail, Integer userId) {
        registeredProducer.customerRegistered(
                RegisteredRequest.builder()
                        .userEmail(userEmail)
                        .userId(userId)
                        .build()
        );
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
        try {
            var auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );

            AppUser appUser = (AppUser) auth.getPrincipal();

            var claims = new HashMap<String, Object>();
            claims.put("email", appUser.getEmail());
            var jwt = jwtService.generateToken(claims, appUser);

            UserResponse user = null;
            if (appUser.getRole() == RoleType.USER) {
                user = userClient.getUser(appUser.getEmail(), String.valueOf(appUser.getUserId()));
            } else if (appUser.getRole() == RoleType.CUSTOMER) {
                user = customerClient.getUser(appUser.getEmail(), String.valueOf(appUser.getUserId()));
            }

            assert user != null;
            return AuthenticationResponse.builder()
                    .token(jwt)
                    .email(user.email())
                    .firstName(user.firstName())
                    .lastName(user.lastName())
                    .role(!Objects.equals(user.role(), "") ? user.role() : "")
                    .build();

        } catch (DisabledException ex) {
            AppUser appUser = appUserRepository.findByEmail(request.email())
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
            sendValidationEmail(appUser);
            throw new DisabledException("Your account is not activated. A new activation email has been sent.");
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Invalid email or password", ex);
        }
    }


    public ServerResponse<Boolean> activateAccount(String token) {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenNotFoundException("Token not found"));

        AppUser appUser = appUserRepository.findById(savedToken.getAppUser().getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(appUser);
            throw new TokenExpiredException("Token has expired. New token has been sent");
        }

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

    public Integer validateToken(String token, String email) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        if (!jwtService.isTokenValid(token, userDetails)) {
            throw new BadCredentialsException("User not found");
        }
        AppUser appUser = (AppUser) userDetails;
        return appUser.getUserId();
    }

    public Boolean existsByEmail(String email) {
        return appUserRepository.findByEmail(email).isPresent();
    }
}
