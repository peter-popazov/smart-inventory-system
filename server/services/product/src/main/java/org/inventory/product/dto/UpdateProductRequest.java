package org.inventory.product.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record UpdateProductRequest(

        @PositiveOrZero
        Integer inventoryId,

        @PositiveOrZero
        Integer productId,

        @NotBlank(message = "Product code is required")
        String productCode,

        @NotBlank(message = "Barcode is required")
        String barcode,

        @NotBlank(message = "Name is required")
        String productName,

        @NotBlank(message = "Description is required")
        @Size(max = 500, message = "Description should not exceed 500 characters")
        String description,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
        BigDecimal price,

        Double weight,

        Double height,

        Double depth,

        Double width,

        @NotEmpty(message = "Category name is required")
        @NotNull(message = "Category name is required")
        String categoryName,

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