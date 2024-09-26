package org.inventory.product.product.inventory;

import jakarta.validation.constraints.PositiveOrZero;

public record UpdateInventoryRequest(

        @PositiveOrZero(message = "Quantity available must be zero or positive")
        Integer quantityAvailable,

        @PositiveOrZero(message = "Min stock level must be zero or positive")
        Integer minStockLevel,

        @PositiveOrZero(message = "Max stock level must be zero or positive")
        Integer maxStockLevel
) {
}
