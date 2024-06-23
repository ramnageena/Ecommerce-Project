package net.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "category_tbl")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    List<Product> products;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    @NotEmpty(message = "Category should not be empty")
    @Size(min = 4, message = "Category name must contain at least 4 characters")
    private String categoryName;
}
