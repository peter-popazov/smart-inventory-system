package org.inventory.product.dto;

import lombok.Builder;

@Builder
public record InventoryResponse(

        Integer inventoryId,
        Integer stockAvailable,
        WarehouseResponse warehouse
) {
}
