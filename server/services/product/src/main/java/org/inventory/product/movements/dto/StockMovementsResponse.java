package org.inventory.product.movements.dto;

import lombok.Builder;
import org.inventory.product.movements.StockMovementType;

@Builder
public record StockMovementsResponse(

        Integer stockMovementId,
        String productName,
        Integer productId,
        Integer quantity,
        Integer productQuantityUpdated,
        StockMovementType movementType,
        String date
) {
}
