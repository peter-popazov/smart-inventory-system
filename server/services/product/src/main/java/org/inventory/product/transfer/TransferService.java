package org.inventory.product.transfer;

import lombok.RequiredArgsConstructor;
import org.inventory.product.ServerResponse;
import org.inventory.product.dto.TransferRequest;
import org.inventory.product.exceptions.InsufficientQuantityException;
import org.inventory.product.exceptions.ProductNotFoundException;
import org.inventory.product.inventory.Inventory;
import org.inventory.product.inventory.InventoryRepository;
import org.inventory.product.product.Product;
import org.inventory.product.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final TransferClient warehouseClient;

    public ServerResponse<String> transferProduct(TransferRequest request) {
        if (Objects.equals(request.warehouseIdFrom(), request.warehouseIdTo())) {
            return ServerResponse.<String>builder().response("Cannot perform transfer to the same warehouse").build();
        }

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        // todo
        Inventory inventoryFrom = inventoryRepository.findById(request.inventoryIdFrom())
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        if (inventoryFrom.getStockAvailable() < request.quantity()) {
            throw new InsufficientQuantityException("Insufficient product quantity for transfer");
        }

        warehouseClient.transferProduct(request);

        inventoryFrom.setStockAvailable(inventoryFrom.getStockAvailable() - request.quantity());

        Inventory inventoryNew = Inventory.builder()
                .createdAt(LocalDateTime.now())
                .stockAvailable(request.quantity())
                .warehouseId(request.warehouseIdTo())
                .product(product)
                .build();

        inventoryRepository.save(inventoryNew);
        productRepository.save(product);

        return ServerResponse.<String>builder().response("Transfer successful").build();
    }
}
