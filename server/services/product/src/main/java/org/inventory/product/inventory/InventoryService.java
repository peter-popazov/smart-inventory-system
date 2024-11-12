package org.inventory.product.inventory;

import lombok.RequiredArgsConstructor;
import org.inventory.product.ServerResponse;
import org.inventory.product.dto.InventoryResponse;
import org.inventory.product.dto.UpdateInventoryRequest;
import org.inventory.product.dto.WarehouseResponse;
import org.inventory.product.exceptions.InventoryNotFoundException;
import org.inventory.product.exceptions.ProductNotFoundException;
import org.inventory.product.lowstock.LowStockAlertService;
import org.inventory.product.product.Product;
import org.inventory.product.product.ProductRepository;
import org.inventory.product.product.WarehouseClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.inventory.product.helpers.UserHelpers.validateUser;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final WarehouseClient warehouseClient;
    private final LowStockAlertService lowStockAlertService;

    public List<InventoryResponse> getInventoryForProduct(Integer productId, String userId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

        validateUser(userId, product.getUserId());

        List<Inventory> productInventory = product.getInventory();

        return productInventory.stream()
                .map(inventory -> {
                    WarehouseResponse warehouse = warehouseClient.getWarehouseById(inventory.getWarehouseId());
                    return InventoryMapper.toInventoryResponse(inventory, warehouse);
                }).toList();
    }

    public ServerResponse<String> updateInventoryForProduct(Integer productId, UpdateInventoryRequest request, String userId) {

        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isEmpty()) {
            return ServerResponse.<String>builder()
                    .response("Product with ID " + productId + " not found.")
                    .build();
        }

        Product product = productOptional.get();

        validateUser(userId, product.getUserId());

        Inventory inventoryToUpdate = inventoryRepository.findById(request.inventoryId())
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with id: " + request.inventoryId()));

        inventoryToUpdate.setStockAvailable(request.quantityAvailable());
        inventoryToUpdate.setWarehouseId(request.warehouseId());

        productRepository.save(product);

        return ServerResponse.<String>builder()
                .response("Inventory updated successfully for product ID " + productId)
                .build();
    }

    public void checkLowStockAlert(Product product) {
        if (product == null) {
            throw new ProductNotFoundException("Product cannot be null");
        }

        int minStockLevel = product.getMinStockLevel();

        int totalStock = product.getCurrentStock();

        if (totalStock < minStockLevel) {
            System.out.printf("LOW STOCK ALERT for product: %s, Total Stock: %d%n",
                    product.getName(), totalStock);
            lowStockAlertService.addOrMergeAlert(product);
        } else {
            System.out.printf("Sufficient stock available for product: %s, Total Stock: %d%n",
                    product.getName(), totalStock);
        }
    }

}
