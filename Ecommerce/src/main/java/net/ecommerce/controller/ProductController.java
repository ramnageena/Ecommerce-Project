package net.ecommerce.controller;

import jakarta.validation.Valid;
import net.ecommerce.payload.ProductDto;
import net.ecommerce.payload.ProductResponse;
import net.ecommerce.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts() {
        ProductResponse productResponse = productService.getAllProducts();
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @PostMapping("/admin/{categoryId}/product")
    public ResponseEntity<ProductDto> addProduct(@Valid @RequestBody ProductDto productDto, @PathVariable Long categoryId) {
        ProductDto savedProductDto = productService.addProduct(productDto, categoryId);
        return new ResponseEntity<>(savedProductDto, HttpStatus.CREATED);

    }

    @GetMapping("/public/{categoryId}/products")
    public ResponseEntity<ProductResponse> searchProductByCategoryId(@PathVariable Long categoryId) {
        ProductResponse productResponse = productService.searchProductByCategoryId(categoryId);
        return new ResponseEntity<>(productResponse, HttpStatus.FOUND);
    }

    @PutMapping("/admin/product/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto, @PathVariable Long productId) {
        ProductDto savedProductDto = productService.updateProduct(productDto, productId);
        return new ResponseEntity<>(savedProductDto, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/admin/product/{productId}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Long productId) {
        productService.deleteCategoryById(productId);
        return new ResponseEntity<>("Product Deleted Successfully!!!", HttpStatus.OK);
    }
}
