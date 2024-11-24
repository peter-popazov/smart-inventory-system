package org.peter.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

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
