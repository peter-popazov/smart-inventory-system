package org.inventory.appuser.user.helpers;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UpdateUserRequest(
        @Email(message = "Email is not valid.") String email,
        @NotNull(message = "Password is required") String password,
        @NotNull(message = "Lastname is required") String firstName,
        @NotNull(message = "Firstname is required") String lastName

) {
}
