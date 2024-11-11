package org.inventory.product.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductStats(

        BigDecimal inventoryValue,
        Integer totalItems
) {
}
