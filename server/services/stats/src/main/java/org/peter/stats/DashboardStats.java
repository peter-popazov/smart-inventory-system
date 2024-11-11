package org.peter.stats;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record DashboardStats(

        BigDecimal totalIncome,
        BigDecimal inventoryValue,
        Integer totalItems,
        Integer teamSize
) {
}
