package org.inventory.product.movements.dto;

import lombok.Builder;
import org.inventory.product.movements.StockMovementType;

@Builder
public record StockMovementsRequest(

        Integer productId,
        Integer warehouseId,
        Integer quantity,
        StockMovementType movementType
) {
}
