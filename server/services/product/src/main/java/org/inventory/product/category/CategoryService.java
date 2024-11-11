package org.inventory.product.category;

import lombok.RequiredArgsConstructor;
import org.inventory.product.ServerResponse;
import org.inventory.product.dto.CategoryResponse;
import org.inventory.product.dto.CategoryStats;
import org.inventory.product.dto.CreateCategoryRequest;
import org.inventory.product.exceptions.CategoryNotFoundException;
import org.inventory.product.product.Product;
import org.inventory.product.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.inventory.product.helpers.UserHelpers.*;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public List<CategoryResponse> getAllCategories(String loggedInUserId, String teamAdminId) {
        Integer adminId = Integer.parseInt(!Objects.equals(teamAdminId, "") ? teamAdminId : loggedInUserId);

        return categoryRepository.findAllByUserId(adminId).stream()
                .distinct()
                .map(CategoryMapper::toCategoryResponse).toList();
    }

    public CategoryResponse getCategoryByName(String name, String userId) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        validateUser(userId, category.getProducts().get(0).getUserId());

        return CategoryMapper.toCategoryResponse(category);
    }

    public ServerResponse<String> createCategory(CreateCategoryRequest request,
                                                 String loggedInUserId, String teamAdminId) {
        Integer adminId = Integer.parseInt(!Objects.equals(teamAdminId, "") ? teamAdminId : loggedInUserId);

        Category category = categoryRepository.findByName(request.name())
                .orElseGet(() -> Category.builder()
                        .name(request.name())
                        .userId(adminId)
                        .build());

        if (category != null) {
            categoryRepository.save(category);
        }

        assert category != null;
        return ServerResponse.<String>builder()
                .response(category.getName())
                .build();
    }

    public CategoryResponse updateCategory(String name, CreateCategoryRequest request, String userId) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        validateUser(userId, category.getProducts().get(0).getUserId());

        if (request.name() != null) {
            category.setName(request.name());
        }

        Category updatedCategory = categoryRepository.save(category);

        return CategoryResponse.builder()
                .id(updatedCategory.getCategoryId())
                .name(updatedCategory.getName())
                .build();
    }

    public ServerResponse<String> deleteCategory(String name, String userId) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        validateUser(userId, category.getProducts().get(0).getUserId());

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

    public List<CategoryStats> getCategoriesForProduct(String loggedInUserId, String teamAdminId) {
        Integer adminId = Integer.parseInt(!Objects.equals(teamAdminId, "") ? teamAdminId : loggedInUserId);
        List<Product> products = productRepository.findAllByUserId(adminId);

        Map<String, Long> categoryCount = products.stream()
                .collect(Collectors.groupingBy(
                        product -> product.getCategory().getName(),
                        Collectors.counting()
                ));

        return categoryCount.entrySet().stream().
                map(e -> new CategoryStats(e.getKey(), e.getValue()))
                .toList();
    }
}
