package org.inventory.product.category;

import lombok.RequiredArgsConstructor;
import org.inventory.product.ServerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable String name) {
        return new ResponseEntity<>(categoryService.getCategoryByName(name), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ServerResponse<String>> createCategory(@RequestBody CreateCategoryRequest request) {
        return new ResponseEntity<>(categoryService.createCategory(request), HttpStatus.CREATED);
    }

    @PutMapping("/{name}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable String name,
                                                           @RequestBody CreateCategoryRequest request) {
        return new ResponseEntity<>(categoryService.updateCategory(name, request), HttpStatus.OK);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<ServerResponse<String>> deleteCategory(@PathVariable String name) {
        return new ResponseEntity<>(categoryService.deleteCategory(name), HttpStatus.ACCEPTED);
    }
}
