package org.peter.warehouse.dto;

import lombok.Builder;

@Builder
public record WarehouseResponse(

        Integer warehouseId,
        String name,
        Boolean isRefrigerated,
        LocationResponse location

) {
}
