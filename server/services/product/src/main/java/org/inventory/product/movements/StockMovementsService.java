package org.inventory.product.movements;

import lombok.RequiredArgsConstructor;
import org.inventory.product.exceptions.InventoryNotFoundException;
import org.inventory.product.exceptions.ProductNotFoundException;
import org.inventory.product.inventory.Inventory;
import org.inventory.product.inventory.InventoryRepository;
import org.inventory.product.movements.dto.StockLevelsResponse;
import org.inventory.product.movements.dto.StockMovementsRequest;
import org.inventory.product.movements.dto.StockMovementsResponse;
import org.inventory.product.movements.helpers.StockMapper;
import org.inventory.product.product.Product;
import org.inventory.product.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockMovementsService {

    private final StockMovementRepository stockMovementRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final StockMapper stockMapper;

    public List<StockMovementsResponse> getMovementsForProduct(Integer productId) {
        return stockMovementRepository.findByProductId(productId).stream()
                .map(stockMapper::toStockMovementsResponse)
                .sorted(Comparator.comparing(StockMovementsResponse::date))
                .toList();
    }

    public void addMovementsForProduct(StockMovementsRequest request) {
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        Inventory inventory = inventoryRepository.findByWarehouseIdAndProduct(request.warehouseId(), product)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found"));

        int newStockLevel;
        switch (request.movementType()) {
            case PURCHASE, ADJUSTMENT:
                newStockLevel = inventory.getStockAvailable() + request.quantity();
                inventory.setStockAvailable(newStockLevel);
                break;
            case SALE:
                newStockLevel = inventory.getStockAvailable() - request.quantity();
                if (newStockLevel < 0) {
                    throw new IllegalArgumentException("Insufficient stock available");
                }
                inventory.setStockAvailable(newStockLevel);
                break;
            default:
                throw new IllegalArgumentException("Invalid stock movement type");
        }

        inventoryRepository.save(inventory);

        StockMovements movement = StockMovements.builder()
                .product(product)
                .quantity(request.quantity())
                .productQuantityUpdated(newStockLevel)
                .movementType(request.movementType())
                .build();

        stockMovementRepository.save(movement);
    }

    public List<StockMovementsResponse> getMovements(String loggedInUserId, String teamAdminId) {
        Integer adminId = Integer.parseInt(!Objects.equals(teamAdminId, "") ? teamAdminId : loggedInUserId);
        return stockMovementRepository.findByUserId(adminId).stream()
                .map(stockMapper::toStockMovementsResponse)
                .toList();
    }

    public List<StockLevelsResponse> findStockStatistics(String loggedInUserId, String teamAdminId) {
        Integer adminId = Integer.parseInt(!Objects.equals(teamAdminId, "") ? teamAdminId : loggedInUserId);
        List<Product> products = productRepository.findAllByUserId(adminId);
        Map<YearMonth, Integer> aggregatedStockLevels = new HashMap<>();

        for (Product product : products) {
            List<StockMovements> movements = product.getStockMovements();
            for (StockMovements movement : movements) {
                YearMonth yearMonth = YearMonth.from(movement.getCreatedAt());
                aggregatedStockLevels.merge(yearMonth, movement.getProductQuantityUpdated(), Integer::sum);
            }
        }

        List<Map.Entry<YearMonth, Integer>> sortedEntries = aggregatedStockLevels.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .toList();

        int cumulativeStock = 0;
        List<StockLevelsResponse> stockLevels = new ArrayList<>();
        for (Map.Entry<YearMonth, Integer> entry : sortedEntries) {
            cumulativeStock += entry.getValue(); // Update the rolling total
            stockLevels.add(
                    StockLevelsResponse.builder()
                            .date(entry.getKey().atEndOfMonth()) // Convert YearMonth to end of the month date
                            .level(cumulativeStock) // Rolling total stock level
                            .build()
            );
        }

        return stockLevels;
    }

}
