package org.inventory.appuser.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inventory.appuser.user.helpers.UpdateUserRequest;
import org.inventory.appuser.user.model.AppUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService service;

    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody @Valid UpdateUserRequest user) {
        return ResponseEntity.ok(service.updateUser(user));
    }

    @GetMapping("/{email}")
    public ResponseEntity<AppUser> getUser(@PathVariable String email) {
        return ResponseEntity.ok(service.getUser(email));
    }

    @GetMapping("/exists/{email}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.existsByEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable String email) {
        return ResponseEntity.ok(service.deleteUser(email));
    }
}