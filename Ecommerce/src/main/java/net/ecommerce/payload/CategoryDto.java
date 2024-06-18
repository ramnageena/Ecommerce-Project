package net.ecommerce.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long categoryId;

    @NotEmpty(message = "Category should not be empty")
    @Size(min = 4, message = "Category name must contain at least 4 characters")
    private String categoryName;


}
