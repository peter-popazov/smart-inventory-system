package org.inventory.appuser.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inventory.appuser.user.helpers.UpdateUserRequest;
import org.inventory.appuser.user.helpers.AppUserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService service;

    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody @Valid UpdateUserRequest user,
                                           @RequestHeader("loggedInUserId") String loggedInUserId) {
        return ResponseEntity.ok(service.updateUser(user, loggedInUserId));
    }

    @PostMapping
    public ResponseEntity<Void> registerUser(@PathVariable("user-id") Integer userId,
                                             @PathVariable("user-email") String userEmail
    ) {
        service.registerUser(userId, userEmail);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{email}")
    public ResponseEntity<AppUserResponse> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.getUserByEmail(email));
    }

    @GetMapping
    public ResponseEntity<AppUserResponse> getUser(@RequestHeader("loggedInUserId") String loggedInUserId) {
        return ResponseEntity.ok(service.getUser(loggedInUserId));
    }

    @GetMapping("/exists/{email}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.existsByEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable String email,
                                              @RequestHeader("loggedInUserId") String loggedInUserId) {
        return ResponseEntity.ok(service.deleteUser(email, loggedInUserId));
    }

    @GetMapping("/team")
    public ResponseEntity<List<Integer>> getUsersByTeamId(@RequestHeader("loggedInUserId") String loggedInUserId) {
        return ResponseEntity.ok(service.getUsersByTeamId(loggedInUserId));
    }
}
