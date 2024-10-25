package org.peter.order.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record PurchaseProductsResponse(

        Integer productId,
        String name,
        String description,
        BigDecimal price,
        Integer quantity,
        List<Integer> warehousesId
) {
}
