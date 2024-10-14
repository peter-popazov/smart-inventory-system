package org.inventory.product.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateProductRequest(

        @NotBlank(message = "Product code is required")
        String productCode,

        @NotBlank(message = "Barcode is required")
        String barcode,

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Description is required")
        @Size(max = 500, message = "Description should not exceed 500 characters")
        String description,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
        BigDecimal price,

        @NotNull(message = "Weight is required")
        @Positive(message = "Weight must be positive")
        Double weight,

        @PositiveOrZero(message = "Height must be zero or positive")
        Double height,

        @PositiveOrZero(message = "Depth must be zero or positive")
        Double depth,

        @PositiveOrZero(message = "Width must be zero or positive")
        Double width,

        @NotEmpty(message = "Category ID is required")
        @NotNull(message = "Category ID is required")
        String category,

        @NotEmpty(message = "Minimal stock level is required")
        @PositiveOrZero(message = "Minimal stock level must be zero or positive")
        Integer minStockLevel,

        @NotEmpty(message = "Max stock level is required")
        @PositiveOrZero(message = "Max stock level must be zero or positive")
        Integer maxStockLevel,

        @NotEmpty(message = "Available quantity is required")
        @PositiveOrZero(message = "Available quantity must be zero or positive")
        Integer quantityAvailable,

        @NotEmpty(message = "Warehouse Id is required")
        @PositiveOrZero(message = "Warehouse Id must be zero or positive")
        Integer warehouseId
) {
}