package net.ecommerce.payload;

import lombok.Data;

import java.util.List;
@Data
public class CategoryResponse {
    List<CategoryDto> content;
}
