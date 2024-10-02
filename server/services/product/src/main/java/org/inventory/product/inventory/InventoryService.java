package org.inventory.product.inventory;

import lombok.RequiredArgsConstructor;
import org.inventory.product.ServerResponse;
import org.inventory.product.exceptions.ProductNotFoundException;
import org.inventory.product.product.Product;
import org.inventory.product.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final ProductRepository productRepository;


    public InventoryResponse getInventoryForProduct(Integer productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

        Inventory productInventory = product.getInventory();

        return InventoryResponse.builder()
                .productName(product.getName())
                .quantityAvailable(productInventory.getQuantityAvailable())
                .maxStockLevel(productInventory.getMaxStockLevel())
                .minStockLevel(productInventory.getMinStockLevel())
                .build();
    }

    public ServerResponse<String> updateInventoryForProduct(Integer productId, UpdateInventoryRequest request) {

        Optional<Product> productOptional = productRepository.findById(productId);


        if (productOptional.isEmpty()) {
            return ServerResponse.<String>builder()
                    .response("Product with ID " + productId + " not found.")
                    .build();
        }

        Product product = productOptional.get();

        Inventory inventoryToUpdate = product.getInventory();

        inventoryToUpdate.setQuantityAvailable(request.quantityAvailable());
        inventoryToUpdate.setMinStockLevel(request.minStockLevel());
        inventoryToUpdate.setMaxStockLevel(request.maxStockLevel());

        productRepository.save(product);

        return ServerResponse.<String>builder()
                .response("Inventory updated successfully for product ID " + productId)
                .build();
    }
}
