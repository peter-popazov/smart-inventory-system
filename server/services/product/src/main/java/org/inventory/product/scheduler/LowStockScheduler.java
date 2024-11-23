package org.inventory.product.scheduler;

import lombok.RequiredArgsConstructor;
import org.inventory.product.inventory.InventoryService;
import org.inventory.product.product.ProductRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LowStockScheduler {

    private final InventoryService inventoryService;
    private final ProductRepository productRepository;

    // every 10 m
    @Scheduled(fixedRate = 1000 * 60 * 30)
    public void checkLowStock() {
        productRepository.findAll().forEach(inventoryService::checkLowStockAlert);
    }
}
