package net.ecommerce.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.ecommerce.exception.ResourceNotFoundException;
import net.ecommerce.model.Category;
import net.ecommerce.model.Product;
import net.ecommerce.payload.ProductDto;
import net.ecommerce.payload.ProductResponse;
import net.ecommerce.repository.CategoryRepository;
import net.ecommerce.repository.ProductRepository;
import net.ecommerce.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public ProductResponse getAllProducts() {
        log.info("ProductServiceImpl.getAllProduct()!!!");
        List<Product> productList = productRepository.findAll();

        //Null check  --stated
        if (productList.isEmpty()) {
            throw new ResourceNotFoundException("Product is Not exist!!!!");
        }
        //  -- ended
        List<ProductDto> productDtoList = productList.stream().map(product -> modelMapper.map(product, ProductDto.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDtoList);
        return productResponse;
    }

    @Override
    public ProductDto addProduct(ProductDto productDto, Long categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        log.info("ProductServiceImpl.addProduct()......categoryId :{} ", category.getCategoryId());
        //Null check --additional  --stated
        boolean isProductNotAvailable = true;
        List<Product> products = category.getProducts();
        for (Product product : products) {
            if (product.getProductName().equals(productDto.getProductName())) {
                isProductNotAvailable = false;
                break;
            }
        }
        // --ended
        if (isProductNotAvailable) {
            Product product = modelMapper.map(productDto, Product.class);
            product.setImage("default.png");
            product.setCategory(category);
            double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
            product.setSpecialPrice(specialPrice);
            Product saveProduct = productRepository.save(product);

            return modelMapper.map(saveProduct, ProductDto.class);
        } else {
            throw new ResourceNotFoundException("Product is already exist!!!!");
        }
    }

    @Override
    public ProductResponse searchProductByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "CatgoryId", categoryId));
        log.info("ProductServiceImpl.searchProductByCategoryId()......categoryId:{} ", category.getCategoryId());
        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
        List<ProductDto> productDtoList = products.stream().map(product -> modelMapper.map(product, ProductDto.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDtoList);
        return productResponse;
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Long productId) {
        // Get the existing product from DB
        Product productFromDb = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        log.info("ProductServiceImpl.updateProduct()...... productId:{} ", productFromDb.getProductId());

        Product product = modelMapper.map(productDto, Product.class);

        // Update the product info with the one in request body
        productFromDb.setProductName(product.getProductName());
        productFromDb.setDescription(product.getDescription());
        productFromDb.setQuantity(product.getQuantity());
        productFromDb.setDiscount(product.getDiscount());
        productFromDb.setPrice(product.getPrice());
        double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
        productFromDb.setSpecialPrice(specialPrice);

        // Save to database
        Product savedProduct = productRepository.save(productFromDb);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public void deleteCategoryById(Long productId) {
        log.info("ProductServiceImpl.deleteCategoryById()......productId:{} ", productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        productRepository.delete(product);


    }
}
