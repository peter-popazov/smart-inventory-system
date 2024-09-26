package org.inventory.product.product.product;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponse(

        String productCode,
        String name,
        String description,
        String photoUrl,
        BigDecimal price,
        String categoryName,
        Integer minStockLevel,
        Integer maxStockLevel,
        Integer availableQuantity
) {
}
