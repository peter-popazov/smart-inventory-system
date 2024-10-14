package org.peter.warehouse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

public record TransferRequest(

        @NotBlank @NotEmpty
        Integer productId,

        @NotBlank @NotEmpty
        Integer warehouseIdFrom,

        @NotBlank @NotEmpty
        Integer warehouseIdTo,

        @NotBlank @NotEmpty
        Integer quantity,

        @NotBlank @NotEmpty
        LocalDateTime sendDate,

        String status,

        @NotBlank @NotEmpty
        LocalDateTime receiveDate
) {
}
