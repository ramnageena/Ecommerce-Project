package net.ecommerce.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long productId;
    @NotEmpty(message = "Product should not be empty")
    @Size(min = 4, message = "Product name must contain at least 4 characters")
    private String productName;
    private String image;
    @NotEmpty(message = "Product should not be empty")
    @Size(min = 6, message = "Product description must contain at least 6 characters")
    private String description;
    private Integer quantity;
    private double price;
    private double discount;
    private double specialPrice;
}
