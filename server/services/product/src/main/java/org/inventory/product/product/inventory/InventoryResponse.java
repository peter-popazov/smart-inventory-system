package org.inventory.product.product.inventory;

import lombok.Builder;

@Builder
public record InventoryResponse(

        String productName,
        Integer quantityAvailable,
        Integer minStockLevel,
        Integer maxStockLevel
) {
}
