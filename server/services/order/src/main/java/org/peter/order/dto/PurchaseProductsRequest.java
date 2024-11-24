package org.peter.order.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PurchaseProductsRequest(

        @NotNull(message = "Product is required")
        Integer productId,

        Integer warehouseId,

        @NotNull(message = "Quantity is required")
        Integer quantity,

        @NotNull(message = "Unit price is required")
        BigDecimal unitPrice
) {


}
