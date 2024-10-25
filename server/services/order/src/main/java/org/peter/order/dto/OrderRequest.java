package org.peter.order.dto;

import jakarta.validation.constraints.NotNull;
import org.peter.order.helpers.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Integer id,
        String reference,

        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod,

        @NotNull(message = "Payment method is required")
        String customerId,

        @NotNull(message = "Total amount is required")
        BigDecimal totalAmount,

        @NotNull(message = "Purchased products are required")
        List<PurchaseProductsRequest> products
) {
}
