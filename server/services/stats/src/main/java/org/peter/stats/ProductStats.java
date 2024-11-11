package org.peter.stats;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductStats(

        BigDecimal inventoryValue,
        Integer totalItems
) {
}
