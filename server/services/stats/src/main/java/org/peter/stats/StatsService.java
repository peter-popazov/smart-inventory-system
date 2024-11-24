package org.peter.stats;

import lombok.RequiredArgsConstructor;
import org.peter.stats.client.AppUserClient;
import org.peter.stats.client.OrdersClient;
import org.peter.stats.client.ProductClient;
import org.peter.stats.dto.StatisticsPercentageChange;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final StatsRepository statsRepository;
    private final ProductClient productClient;
    private final AppUserClient appUserClient;
    private final OrdersClient ordersClient;

    public void saveStats(ProductStats stats, String userId, BigDecimal totalIncome) {
        statsRepository.save(Stats.builder()
                .inventoryValue(stats.inventoryValue())
                .totalItems(stats.totalItems())
                .totalIncome(totalIncome)
                .userId(Integer.parseInt(userId))
                .build());
    }

    public DashboardStats getDashboardStats(String loggedInUserId, String teamAdminId) {
        String userId = String.valueOf(!Objects.equals(teamAdminId, "") ? teamAdminId : loggedInUserId);
        ProductStats productStats = productClient.getProductStats(userId);
        BigDecimal totalIncome = ordersClient.getOrdersStats(userId);
        Integer teamSize = appUserClient.getTeamsStats(loggedInUserId);
        saveStats(productStats, userId, totalIncome);

        StatisticsPercentageChange statsPercent = calculatePercentageChange(statsRepository.findByUserId(Integer.parseInt(userId)));

        return DashboardStats.builder()
                .inventoryValue(productStats.inventoryValue())
                .totalItems(productStats.totalItems())
                .totalIncome(totalIncome)
                .incomeChangePercentage(statsPercent.totalIncome())
                .inventoryChangePercentage(statsPercent.inventoryValue())
                .teamSize(teamSize)
                .build();
    }


    private StatisticsPercentageChange calculatePercentageChange(List<Stats> statistics) {
        Map<YearMonth, List<Stats>> monthlyStats = statistics.stream()
                .collect(Collectors.groupingBy(stat -> YearMonth.from(stat.getCreatedAt())));

        List<YearMonth> sortedMonths = monthlyStats.keySet().stream()
                .sorted()
                .toList();

        if (sortedMonths.size() < 2) {
            return StatisticsPercentageChange.builder()
                    .totalIncome(BigDecimal.ZERO)
                    .inventoryValue(BigDecimal.ZERO)
                    .build();
        }

        YearMonth currentMonth = sortedMonths.get(sortedMonths.size() - 1);
        YearMonth previousMonth = sortedMonths.get(sortedMonths.size() - 2);

        Stats lastCurrentMonthStats = monthlyStats.get(currentMonth).stream()
                .max(Comparator.comparing(Stats::getCreatedAt))
                .orElseThrow(() -> new IllegalStateException("No stats found for the current month."));

        Stats lastPreviousMonthStats = monthlyStats.get(previousMonth).stream()
                .max(Comparator.comparing(Stats::getCreatedAt))
                .orElseThrow(() -> new IllegalStateException("No stats found for the previous month."));

        BigDecimal inventoryChange = lastPreviousMonthStats.getInventoryValue().compareTo(BigDecimal.ZERO) != 0
                ? lastCurrentMonthStats.getInventoryValue()
                .subtract(lastPreviousMonthStats.getInventoryValue())
                .divide(lastPreviousMonthStats.getInventoryValue(), RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                : BigDecimal.ZERO;

        BigDecimal incomeChange = lastPreviousMonthStats.getTotalIncome().compareTo(BigDecimal.ZERO) != 0
                ? lastCurrentMonthStats.getTotalIncome()
                .subtract(lastPreviousMonthStats.getTotalIncome())
                .divide(lastPreviousMonthStats.getTotalIncome(), RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                : BigDecimal.ZERO;

        return StatisticsPercentageChange.builder()
                .inventoryValue(inventoryChange)
                .totalIncome(incomeChange)
                .build();
    }



}
