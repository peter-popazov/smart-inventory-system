package org.inventory.product.clients.dto;

import lombok.Builder;

@Builder
public record AppUserResponse(

        Integer userId,
        String email,
        String firstName,
        String lastName,
        String role
) {
}
