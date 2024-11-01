package org.inventory.product.dto;

import lombok.Builder;

@Builder
public record CategoryResponse(

        Integer id,
        String name
) {
}
