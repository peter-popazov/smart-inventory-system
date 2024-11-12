package org.inventory.product.lowstock;

import lombok.RequiredArgsConstructor;
import org.inventory.product.lowstock.dto.LowStockAlertResponse;
import org.inventory.product.lowstock.helpers.LowStockAlertMapper;
import org.inventory.product.product.Product;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LowStockAlertService {

    private final LowStockAlertRepository lowStockAlertRepository;
    private final LowStockAlertMapper lowStockAlertMapper;

    public List<LowStockAlertResponse> getAlerts(String userId, String teamAdminId) {
        Integer adminId = Integer.parseInt(!Objects.equals(teamAdminId, "") ? teamAdminId : userId);
        return lowStockAlertRepository.findAllByUserId(adminId).stream()
                .map(lowStockAlertMapper::toLowStockAlertResponse)
                .toList();
    }

    public void addOrMergeAlert(Product product) {

        Optional<LowStockAlert> existingAlertOpt = lowStockAlertRepository.findByProductAndActive(product, true);
        if (existingAlertOpt.isPresent()) {
            LowStockAlert existingAlert = existingAlertOpt.get();
            existingAlert.setUpdatedAt(LocalDateTime.now());
        } else {
            lowStockAlertRepository.save(LowStockAlert.builder()
                    .product(product)
                    .active(true)
                    .build());
        }
    }

    public void processAlert(Integer alertId) {
        LowStockAlert alert = lowStockAlertRepository.findByAlertIdAndActive(alertId, true)
                .orElseThrow(() -> new RuntimeException("Alert not found"));

        // todo generate report

        // todo send to mail

        alert.setActive(false);
    }
}
