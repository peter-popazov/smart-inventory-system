package org.peter.order.dto;

import lombok.Builder;

@Builder
public record StockMovementsRequest(

        Integer productId,
        Integer warehouseId,
        Integer quantity,
        StockMovementType movementType
) {
}
