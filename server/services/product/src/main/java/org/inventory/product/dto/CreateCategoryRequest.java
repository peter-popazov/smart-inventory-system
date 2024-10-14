package org.inventory.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record CreateCategoryRequest(

        @NotEmpty(message = "Category name is required")
        @NotBlank(message = "Category name is required")
        String name

) {
}
