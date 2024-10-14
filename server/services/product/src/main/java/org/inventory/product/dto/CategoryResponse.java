package org.inventory.product.dto;

import lombok.Builder;

@Builder
public record CategoryResponse(

        String name
) {
}
