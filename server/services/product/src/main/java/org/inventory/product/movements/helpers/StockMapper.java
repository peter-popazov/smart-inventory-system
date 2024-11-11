package org.inventory.product.movements.helpers;

import lombok.RequiredArgsConstructor;
import org.inventory.product.movements.StockMovements;
import org.inventory.product.movements.dto.StockMovementsResponse;
import org.inventory.product.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class StockMapper {

    private final ProductRepository productRepository;

    public StockMovementsResponse toStockMovementsResponse(StockMovements stockMovements) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        int productId = stockMovements.getProduct().getProductId();
        return StockMovementsResponse.builder()
                .stockMovementId(stockMovements.getStockMovementId())
                .productId(productId)
                .productName(productRepository.findProductNameById(productId))
                .quantity(stockMovements.getQuantity())
                .productQuantityUpdated(stockMovements.getProductQuantityUpdated())
                .movementType(stockMovements.getMovementType())
                .date(stockMovements.getCreatedAt().format(formatter))
                .build();
    }
}
