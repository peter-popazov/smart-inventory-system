package org.peter.auth.helpers;

import lombok.Builder;

@Builder
public record UserResponse(

        String email,
        String firstName,
        String lastName,
        String role
) {
}
