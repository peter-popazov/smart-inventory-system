package org.peter.warehouse.transfer;

import lombok.Builder;
import org.peter.warehouse.dto.WarehouseResponse;

import java.time.LocalDateTime;

@Builder
public record TransferResponse(

    Integer transferId,
    Integer quantity,
    LocalDateTime sendDate,
    LocalDateTime receiveDate,
    Integer productId,
    String transferStatus,
    WarehouseResponse warehouseTo,
    WarehouseResponse warehouseFrom

) {
}
