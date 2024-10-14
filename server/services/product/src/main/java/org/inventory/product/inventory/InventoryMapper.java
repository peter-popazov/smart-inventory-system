package org.inventory.product.inventory;

import org.inventory.product.dto.InventoryResponse;
import org.inventory.product.dto.WarehouseResponse;

public class InventoryMapper {

    public static InventoryResponse toInventoryResponse(Inventory inventory, WarehouseResponse warehouse) {
        return InventoryResponse.builder()
                .stockAvailable(inventory.getStockAvailable())
                .warehouse(warehouse)
                .build();
    }
}