package org.inventory.appuser.user.helpers;

import jakarta.validation.constraints.*;

public record RegisterUserRequest(

        @NotEmpty(message = "Firstname is required")
        @NotBlank(message = "Firstname is required")
        String firstName,

        @NotEmpty(message = "Lastname is required")
        @NotBlank(message = "Lastname is required")
        String lastName,

        @NotEmpty(message = "Email is required")
        @NotBlank(message = "Email is required")
        @Email(message = "Email is not valid")
        String email,

        @NotEmpty(message = "Password is required")
        @NotBlank(message = "Password is required")
        @Size(min=8, message = "Password should be 8 characters long minimum")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password should contain at least one letter and one number")
        String password,

        String role
        ) {
}
