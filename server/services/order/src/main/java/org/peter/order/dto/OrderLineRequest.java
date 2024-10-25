package org.peter.order.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderLineRequest(

        Integer productId,
        Integer orderId,
        BigDecimal unitPrice,
        double quantity
) {
}
