package org.inventory.product.dto;

import lombok.Builder;

@Builder
public record InventoryResponse(

        Integer stockAvailable,
        WarehouseResponse warehouse
) {
}
