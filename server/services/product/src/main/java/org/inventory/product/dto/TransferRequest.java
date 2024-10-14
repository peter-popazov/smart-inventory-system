package org.inventory.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record TransferRequest(

        @NotBlank @NotEmpty
        Integer productId,

        @NotBlank @NotEmpty
        Integer warehouseIdFrom,

        @NotBlank @NotEmpty
        Integer warehouseIdTo,

        @Positive(message = "Quantity should be grater than 0")
        Integer quantity,

        @NotBlank @NotEmpty
        LocalDateTime sendDate,

        @NotBlank @NotEmpty
        LocalDateTime receiveDate,

        @NotBlank @NotEmpty
        String status,

        @Positive
        Integer inventoryIdFrom
) {
}
