package org.inventory.product.product;

import lombok.RequiredArgsConstructor;
import org.inventory.product.dto.InventoryResponse;
import org.inventory.product.dto.ProductResponse;
import org.inventory.product.dto.WarehouseResponse;
import org.inventory.product.inventory.InventoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductMapper {

    private final WarehouseClient warehouseClient;

    public ProductResponse toProductResponse(Product product) {
        List<InventoryResponse> inventories = product.getInventory().stream()
                .map(inventory -> {
                    WarehouseResponse warehouse = warehouseClient.getWarehouseById(inventory.getWarehouseId());
                    return InventoryMapper.toInventoryResponse(inventory, warehouse);
                })
                .toList();

        return ProductResponse.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .productCode(product.getProductCode())
                .inventories(inventories)
                .categoryName(product.getCategory().getName())
                .photoUrl("URL")
                .maxStockLevel(product.getMaxStockLevel())
                .minStockLevel(product.getMinStockLevel())
                .build();
    }
}
