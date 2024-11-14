package org.inventory.product.lowstock.helpers;

import org.inventory.product.lowstock.LowStockAlert;
import org.inventory.product.lowstock.dto.LowStockAlertResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class LowStockAlertMapper {

    public LowStockAlertResponse toLowStockAlertResponse(LowStockAlert alert) {

        Integer currentQuantity = alert.getProduct().getCurrentStock();
        Integer reorderStock = alert.getProduct().getMinStockLevel();
        Integer maxQuantity = alert.getProduct().getMaxStockLevel();
        BigDecimal quantityDifference = BigDecimal.valueOf(maxQuantity - currentQuantity);
        BigDecimal reorderPrice = alert.getProduct().getPrice().multiply(quantityDifference);

        return LowStockAlertResponse.builder()
                .alertId(alert.getAlertId())
                .productId(alert.getProduct().getProductId())
                .productName(alert.getProduct().getName())
                .currentQuantity(currentQuantity)
                .reorderStock(reorderStock)
                .reorderPrice(reorderPrice)
                .build();
    }
}
