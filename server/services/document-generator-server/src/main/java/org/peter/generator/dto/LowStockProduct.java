package org.peter.generator.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record LowStockProduct(
        String userEmail,
        String username,
        String productName,
        Integer currentStock,
        Integer maxStock,
        Integer minStock,
        BigDecimal price
) {

    @Override
    public String toString() {
        return "Low stock alert for " + productName;
    }
}
