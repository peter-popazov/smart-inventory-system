package org.inventory.product.dto;

import jakarta.validation.constraints.PositiveOrZero;

public record UpdateInventoryRequest(

        @PositiveOrZero(message = "Inventory ID must be zero or positive")
        Integer inventoryId,

        @PositiveOrZero(message = "Quantity available must be zero or positive")
        Integer quantityAvailable,

        @PositiveOrZero(message = "Warehouse id zero or positive")
        Integer warehouseId
) {
}
