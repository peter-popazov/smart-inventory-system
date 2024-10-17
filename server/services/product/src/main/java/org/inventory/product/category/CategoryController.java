package org.inventory.product.category;

import lombok.RequiredArgsConstructor;
import org.inventory.product.ServerResponse;
import org.inventory.product.dto.CategoryResponse;
import org.inventory.product.dto.CreateCategoryRequest;
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
    public ResponseEntity<List<CategoryResponse>> getAllCategories(@RequestHeader("loggedInUserId") String loggedInUserId) {
        return new ResponseEntity<>(categoryService.getAllCategories(loggedInUserId), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable String name,
                                                        @RequestHeader("loggedInUserId") String loggedInUserId) {
        return new ResponseEntity<>(categoryService.getCategoryByName(name, loggedInUserId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ServerResponse<String>> createCategory(@RequestBody CreateCategoryRequest request) {
        return new ResponseEntity<>(categoryService.createCategory(request), HttpStatus.CREATED);
    }

    @PutMapping("/{name}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable String name,
                                                           @RequestBody CreateCategoryRequest request,
                                                           @RequestHeader("loggedInUserId") String loggedInUserId) {
        return new ResponseEntity<>(categoryService.updateCategory(name, request, loggedInUserId), HttpStatus.OK);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<ServerResponse<String>> deleteCategory(@PathVariable String name,
                                                                 @RequestHeader("loggedInUserId") String loggedInUserId) {
        return new ResponseEntity<>(categoryService.deleteCategory(name, loggedInUserId), HttpStatus.ACCEPTED);
    }
}
