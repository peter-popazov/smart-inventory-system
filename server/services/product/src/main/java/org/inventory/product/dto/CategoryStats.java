package org.inventory.product.dto;

import lombok.Builder;

@Builder
public record CategoryStats(

        String categoryName,
        Long productsNumber
) {
}
