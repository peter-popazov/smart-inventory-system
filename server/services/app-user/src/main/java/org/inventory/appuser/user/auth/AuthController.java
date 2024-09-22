package org.inventory.appuser.user.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inventory.appuser.user.helpers.AuthUserRequest;
import org.inventory.appuser.user.helpers.AuthenticationResponse;
import org.inventory.appuser.user.helpers.RegisterUserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<Integer> registerUser(@RequestBody @Valid RegisterUserRequest request) {
        return new ResponseEntity<>(service.register(request), HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthUserRequest request) {
        return new ResponseEntity<>(service.authenticate(request), HttpStatus.OK);
    }

    @GetMapping("/activate-account")
    public ResponseEntity<Boolean> activateAccount(@RequestParam String token) {
        return new ResponseEntity<>(service.activateAccount(token), HttpStatus.ACCEPTED);
    }
}
