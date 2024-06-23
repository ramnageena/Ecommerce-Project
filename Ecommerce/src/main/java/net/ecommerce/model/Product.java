package net.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @NotEmpty(message = "Product should not be empty")
    @Size(min = 4, message = "Product name must contain at least 4 characters")
    private  String productName;
    @NotEmpty(message = "Product should not be empty")
    @Size(min = 6, message = "Product description must contain at least 6 characters")
    private String description;
    private String image;
    private Integer quantity;
    private  double price;
    private double discount;
    private double specialPrice;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
