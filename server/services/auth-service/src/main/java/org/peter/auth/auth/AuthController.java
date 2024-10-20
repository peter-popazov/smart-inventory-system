package org.peter.auth.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.peter.auth.ServerResponse;
import org.peter.auth.helpers.AuthUserRequest;
import org.peter.auth.helpers.AuthenticationResponse;
import org.peter.auth.helpers.RegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<ServerResponse<Integer>> register(@RequestBody @Valid RegisterRequest request) {
        return new ResponseEntity<>(service.register(request), HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthUserRequest request) {
        return new ResponseEntity<>(service.authenticate(request), HttpStatus.OK);
    }

    @GetMapping("/activate-account")
    public ResponseEntity<ServerResponse<Boolean>> activateAccount(@RequestParam String token) {
        return new ResponseEntity<>(service.activateAccount(token), HttpStatus.ACCEPTED);
    }

    @GetMapping("/validate-token")
    public ResponseEntity<Integer> validateToken(@RequestParam("token") String token,
                                                 @RequestParam("email") String email) {
        return new ResponseEntity<>(service.validateToken(token, email), HttpStatus.OK);
    }
}
