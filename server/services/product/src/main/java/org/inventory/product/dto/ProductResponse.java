package org.inventory.product.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record ProductResponse(

        Integer productId,
        String productCode,
        String barCode,
        String name,
        String description,
        String photoUrl,
        BigDecimal price,
        String categoryName,
        Integer minStockLevel,
        Integer maxStockLevel,
        List<InventoryResponse> inventories
) {
}
