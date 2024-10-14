package org.inventory.product.dto;

public record WarehouseResponse(

        Integer warehouseId,
        String name,
        Boolean isRefrigerated,
        LocationResponse location
) {
}
