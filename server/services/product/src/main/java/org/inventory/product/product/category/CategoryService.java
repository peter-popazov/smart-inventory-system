package org.inventory.product.product.category;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.inventory.product.product.ServerResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toCategoryResponse)
                .toList();
    }

    public CategoryResponse getCategoryByName(String name) {
        Category category = categoryRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Category not found"));

        return CategoryMapper.toCategoryResponse(category);
    }

    public ServerResponse<String> createCategory(CreateCategoryRequest request) {
        Category category = categoryRepository.findByName(request.name())
                .orElseGet(() -> Category.builder().name(request.name()).build());

        if (category != null) {
            categoryRepository.save(category);
        }

        assert category != null;
        return ServerResponse.<String>builder()
                .response(category.getName())
                .build();
    }

    public CategoryResponse updateCategory(String name, CreateCategoryRequest request) {
        Category category = categoryRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Category not found"));

        if (request.name() != null) {
            category.setName(request.name());
        }

        Category updatedCategory = categoryRepository.save(category);

        return new CategoryResponse(
                updatedCategory.getName()
        );
    }

    public ServerResponse<String> deleteCategory(String name) {
        Category category = categoryRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Category not found"));

        if (!category.getProducts().isEmpty()) {
            return ServerResponse.<String>builder()
                    .response("Category cannot be deleted as it is associated with products.")
                    .build();

        }

        categoryRepository.delete(category);

        return ServerResponse.<String>builder()
                .response("Category was deleted.")
                .build();
    }
}
