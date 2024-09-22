package org.inventory.appuser.user.helpers;

import jakarta.validation.constraints.*;

public record AuthUserRequest(

        @NotEmpty(message = "Email is required")
        @NotBlank(message = "Email is required")
        @Email(message = "Email is not valid")
        String email,

        @NotEmpty(message = "Password is required")
        @NotBlank(message = "Password is required")
        String password
) {
}
