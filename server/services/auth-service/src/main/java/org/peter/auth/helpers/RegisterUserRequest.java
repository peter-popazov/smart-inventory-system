package org.peter.auth.helpers;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RegisterUserRequest(

        @NotEmpty(message = "Email is required")
        @NotBlank(message = "Email is required")
        @Email(message = "Email is not valid")
        String email,

        @NotEmpty(message = "Password is required")
        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password should be 8 characters long minimum")
        String password
) {
}
