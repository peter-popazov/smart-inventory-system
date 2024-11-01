package org.inventory.product.category;

import org.inventory.product.dto.CategoryResponse;

public class CategoryMapper {

    public static CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getCategoryId())
                .name(category.getName())
                .build();
    }
}
