package org.inventory.product.lowstock.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record LowStockAlertResponse(

        Integer alertId,
        Integer productId,
        String productName,
        Integer currentQuantity,
        Integer reorderStock,
        BigDecimal reorderPrice

) {
}
