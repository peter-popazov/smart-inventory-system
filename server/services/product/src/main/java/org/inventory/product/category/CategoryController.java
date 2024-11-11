package org.inventory.product.category;

import lombok.RequiredArgsConstructor;
import org.inventory.product.ServerResponse;
import org.inventory.product.dto.CategoryResponse;
import org.inventory.product.dto.CategoryStats;
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
    public ResponseEntity<List<CategoryResponse>> getAllCategories(@RequestHeader("loggedInUserId") String loggedInUserId,
                                                                   @RequestHeader("teamAdminId") String teamAdminId) {
        return new ResponseEntity<>(categoryService.getAllCategories(loggedInUserId, teamAdminId), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable String name,
                                                        @RequestHeader("loggedInUserId") String loggedInUserId) {
        return new ResponseEntity<>(categoryService.getCategoryByName(name, loggedInUserId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ServerResponse<String>> createCategory(@RequestHeader("loggedInUserId") String loggedInUserId,
                                                                 @RequestHeader("teamAdminId") String teamAdminId,
                                                                 @RequestBody CreateCategoryRequest request) {
        return new ResponseEntity<>(categoryService.createCategory(request, loggedInUserId, teamAdminId), HttpStatus.CREATED);
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

    @GetMapping("/stats")
    public ResponseEntity<List<CategoryStats>> getCategoriesForProduct(@RequestHeader("loggedInUserId") String loggedInUserId,
                                                                       @RequestHeader("teamAdminId") String teamAdminId) {
        return new ResponseEntity<>(categoryService.getCategoriesForProduct(loggedInUserId, teamAdminId), HttpStatus.OK);
    }
}
