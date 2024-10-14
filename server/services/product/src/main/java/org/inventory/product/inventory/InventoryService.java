package org.inventory.product.inventory;

import lombok.RequiredArgsConstructor;
import org.inventory.product.ServerResponse;
import org.inventory.product.dto.InventoryResponse;
import org.inventory.product.dto.UpdateInventoryRequest;
import org.inventory.product.dto.WarehouseResponse;
import org.inventory.product.exceptions.ProductNotFoundException;
import org.inventory.product.product.Product;
import org.inventory.product.product.ProductRepository;
import org.inventory.product.product.WarehouseClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final WarehouseClient warehouseClient;

    public List<InventoryResponse> getInventoryForProduct(Integer productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

        List<Inventory> productInventory = product.getInventory();

        return productInventory.stream()
                .map(inventory -> {
                    WarehouseResponse warehouse = warehouseClient.getWarehouseById(inventory.getWarehouseId());
                    return InventoryMapper.toInventoryResponse(inventory, warehouse);
                }).toList();
    }

    public ServerResponse<String> updateInventoryForProduct(Integer productId, UpdateInventoryRequest request) {

        Optional<Product> productOptional = productRepository.findById(productId);


        if (productOptional.isEmpty()) {
            return ServerResponse.<String>builder()
                    .response("Product with ID " + productId + " not found.")
                    .build();
        }

        Product product = productOptional.get();

        // todo
        Inventory inventoryToUpdate = inventoryRepository.findById(request.inventoryId())
                .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + request.inventoryId()));

        inventoryToUpdate.setStockAvailable(request.quantityAvailable());
        inventoryToUpdate.setWarehouseId(request.warehouseId());

        productRepository.save(product);

        return ServerResponse.<String>builder()
                .response("Inventory updated successfully for product ID " + productId)
                .build();
    }
}
