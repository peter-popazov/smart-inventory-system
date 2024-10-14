package org.peter.warehouse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateWarehouseRequest(

        @NotBlank(message = "Warehouse name is required")
        String name,

        @NotNull(message = "Refrigeration status must be provided")
        Boolean isRefrigerated,

        @NotBlank(message = "Address is required")
        String address,

        @NotBlank(message = "City name is required")
        String city,

        @NotBlank(message = "PostalCode is required")
        String postalCode
) {
}
