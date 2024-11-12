package org.peter.warehouse.dto;

import org.peter.warehouse.transfer.TransferStatus;
import org.peter.warehouse.warehouse.Warehouse;

import java.time.LocalDateTime;

public record TransferResponse(

        Integer quantity,

        LocalDateTime sendDate,

        LocalDateTime receiveDate,

        Integer productId,

        TransferStatus transferStatus,

        Warehouse warehouseTo,

        Warehouse warehouseFrom
) {
}
