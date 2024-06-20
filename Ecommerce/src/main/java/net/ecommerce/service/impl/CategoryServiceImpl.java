package net.ecommerce.service.impl;

import net.ecommerce.exception.ResourceNotFoundException;
import net.ecommerce.model.Category;
import net.ecommerce.payload.CategoryDto;
import net.ecommerce.payload.CategoryResponse;
import net.ecommerce.repository.CategoryRepository;
import net.ecommerce.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    CategoryRepository categoryRepository;
    ModelMapper modelMapper;

    //constructor injection
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        LOGGER.info("Inside CategoryServiceImpl.getCategory() :{} ", categoryId);
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            Category category1 = category.get();
            return modelMapper.map(category1, CategoryDto.class);
        }
        throw new ResourceNotFoundException("Category", "categoryId", categoryId);

    }

    @Override
    public CategoryResponse getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        LOGGER.info("Inside CategoryServiceImpl.getAllCategory !!!!");

        /* Logic for Sorting*/
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();  //using ternary operator

        /* Logic for Paging */
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> categoryListPage = categoryRepository.findAll(pageDetails);
        List<Category> categoryList = categoryListPage.getContent();
        if (categoryList.isEmpty()) {
            throw new ResourceNotFoundException("Category is not created till now");
        }
        List<CategoryDto> categoryDto = categoryList.stream().map(category -> modelMapper.map(category, CategoryDto.class)).toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDto);

        // setting  meta data for paging
        categoryResponse.setPageNumber(categoryListPage.getNumber());
        categoryResponse.setPageSize(categoryListPage.getSize());
        categoryResponse.setTotalElements(categoryListPage.getTotalElements());
        categoryResponse.setTotalPages(categoryListPage.getTotalPages());
        categoryResponse.setLastPage(categoryListPage.isLast());
        return categoryResponse;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category categoryName = categoryRepository.findByCategoryName(categoryDto.getCategoryName());
        if (categoryName != null) {
            throw new ResourceNotFoundException("Category is already exist by this name :" + categoryName.getCategoryName());
        }

        LOGGER.info("Inside CategoryServiceImpl.createCategory() :{} ", categoryDto);
        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
        LOGGER.info("Inside CategoryServiceImpl.updateCategory() :{} ,:{} ", categoryDto, categoryId);
        Category categoryName = categoryRepository.findByCategoryName(categoryDto.getCategoryName());
        if (categoryName != null) {
            throw new ResourceNotFoundException("Category is already exist by this name :" + categoryName.getCategoryName());
        }
        CategoryDto existingCategory = getCategoryById(categoryId);
        existingCategory.setCategoryName(categoryDto.getCategoryName() != null ? categoryDto.getCategoryName() : existingCategory.getCategoryName());
        Category category = modelMapper.map(existingCategory, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public String deleteCategoryById(Long categoryId) {
        LOGGER.info("Inside CategoryServiceImpl.deleteCategory() :{} ", categoryId);
        CategoryDto categoryDto = getCategoryById(categoryId);
        Category category = modelMapper.map(categoryDto, Category.class);
        categoryRepository.delete(category);
        return "Category with categoryId: " + categoryId + " deleted successfully !!";
    }


}

