package org.inventory.product.category;

public class CategoryMapper {

    public static CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .name(category.getName())
                .build();
    }
}
