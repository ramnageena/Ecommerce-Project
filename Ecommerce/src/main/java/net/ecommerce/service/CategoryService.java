package net.ecommerce.service;

import net.ecommerce.model.Category;
import net.ecommerce.payload.CategoryDto;
import net.ecommerce.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryDto getCategoryById(Long categoryId);

   CategoryResponse getAllCategory();

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto,Long categoryId);

    String  deleteCategory(Long categoryId);


}

