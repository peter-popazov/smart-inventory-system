package org.peter.notification.kafka;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record LowStockProduct(
        String userEmail,
        String username,
        String productName,
        Integer currentStock,
        Integer minStock,
        Integer maxStock,
        BigDecimal price
) {

    @Override
    public String toString() {
        return "Low stock alert for " + productName;
    }
}
