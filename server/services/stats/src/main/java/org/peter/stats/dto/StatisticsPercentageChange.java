package org.peter.stats.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record StatisticsPercentageChange(
        BigDecimal inventoryValue,
        BigDecimal totalIncome
) {
}
