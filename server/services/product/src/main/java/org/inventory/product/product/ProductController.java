package org.inventory.product.product;

import lombok.RequiredArgsConstructor;
import org.inventory.product.ServerResponse;
import org.inventory.product.category.CreateProductRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(@RequestHeader("loggedInUserId") String loggedInUserId) {
        return new ResponseEntity<>(productService.getAllProducts(loggedInUserId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Integer id,
                                                          @RequestHeader("loggedInUserId") String loggedInUserId) {
        return new ResponseEntity<>(productService.getProductById(id, loggedInUserId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ServerResponse<Integer>> createProduct(@RequestBody CreateProductRequest product,
                                                                 @RequestHeader("loggedInUserId") String loggedInUserId) {
        return new ResponseEntity<>(productService.createProduct(product, loggedInUserId), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ServerResponse<Integer>> updateProduct(@RequestBody CreateProductRequest productRequest,
                                                                 @PathVariable Integer id, @RequestHeader("loggedInUserId") String loggedInUserId) {
        return new ResponseEntity<>(productService.updateProduct(id, productRequest, loggedInUserId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id, @RequestHeader("loggedInUserId") String loggedInUserId) {
        return new ResponseEntity<>(productService.deleteProductById(id, loggedInUserId), HttpStatus.OK);
    }
}
