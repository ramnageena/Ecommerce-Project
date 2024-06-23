package net.ecommerce.service;

import net.ecommerce.payload.ProductDto;
import net.ecommerce.payload.ProductResponse;

public interface ProductService {
    ProductResponse getAllProducts();

    ProductDto addProduct(ProductDto productDto, Long categoryId);

    ProductResponse searchProductByCategoryId(Long categoryId);

    ProductDto updateProduct(ProductDto productDto, Long productId);

    void deleteCategoryById(Long productId);
}
