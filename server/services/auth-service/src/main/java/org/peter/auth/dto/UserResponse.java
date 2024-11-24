package org.peter.auth.dto;

import lombok.Builder;

@Builder
public record UserResponse(

        String email,
        String firstName,
        String lastName,
        String role
) {
}
