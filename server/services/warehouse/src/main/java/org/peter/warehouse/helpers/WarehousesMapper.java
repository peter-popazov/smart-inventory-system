package org.peter.warehouse.helpers;

import org.peter.warehouse.dto.LocationResponse;
import org.peter.warehouse.dto.WarehouseResponse;
import org.peter.warehouse.warehouse.Warehouse;

public class WarehousesMapper {

    public static WarehouseResponse toResponse(Warehouse warehouse) {
        return WarehouseResponse.builder()
                .warehouseId(warehouse.getWarehouseId())
                .isRefrigerated(warehouse.isRefrigerated())
                .name(warehouse.getName())
                .location(LocationResponse.builder()
                        .city(warehouse.getLocation().getCity())
                        .address(warehouse.getLocation().getAddress())
                        .postalCode(warehouse.getLocation().getPostalCode())
                        .build())
                .build();
    }
}
