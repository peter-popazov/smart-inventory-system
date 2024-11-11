package org.peter.stats;

import lombok.RequiredArgsConstructor;
import org.peter.stats.client.AppUserClient;
import org.peter.stats.client.OrdersClient;
import org.peter.stats.client.ProductClient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

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
        String userId = String.valueOf(Integer.parseInt(!Objects.equals(teamAdminId, "") ? teamAdminId : loggedInUserId));
        ProductStats productStats = productClient.getProductStats(userId);
        BigDecimal totalIncome = ordersClient.getOrdersStats(userId);
        Integer teamSize = appUserClient.getTeamsStats(userId);
        saveStats(productStats, userId, totalIncome);

        return DashboardStats.builder()
                .inventoryValue(productStats.inventoryValue())
                .totalItems(productStats.totalItems())
                .totalIncome(totalIncome)
                .teamSize(teamSize)
                .build();
    }
}
