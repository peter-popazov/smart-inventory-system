package org.inventory.product.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PurchaseProductsRequest(

        @NotNull(message = "Product is required")
        Integer productId,

        @NotNull(message = "Quantity is required")
        Integer quantity,

        @NotNull(message = "Unit price is required")
        BigDecimal unitPrice
) {


}
