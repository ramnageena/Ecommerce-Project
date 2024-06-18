package net.ecommerce.service;

import net.ecommerce.payload.CategoryDto;
import net.ecommerce.payload.CategoryResponse;

public interface CategoryService {

    CategoryDto getCategoryById(Long categoryId);

    CategoryResponse getAllCategory();

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);

    String deleteCategoryById(Long categoryId);


}

