package net.ecommerce.controller;

import jakarta.validation.Valid;
import net.ecommerce.payload.CategoryDto;
import net.ecommerce.payload.CategoryResponse;
import net.ecommerce.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //example for RequestParam
    @GetMapping("/echo")
    public ResponseEntity<String> message(@RequestParam(name = "message",required = false) String message){
        String messageEcho="Echoed message: "+ message;
        return new ResponseEntity<>(messageEcho,HttpStatus.OK);
    }



    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto category = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @GetMapping("/public/categories/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable long categoryId) {
        CategoryDto categoryById = categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(categoryById, HttpStatus.FOUND);
    }

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategory() {
        CategoryResponse allCategory = categoryService.getAllCategory();
        return new ResponseEntity<>(allCategory, HttpStatus.FOUND);
    }

    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Long categoryId) {
        CategoryDto updatedCategory = categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Long categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return new ResponseEntity<>("Category Deleted Successfully!!!", HttpStatus.OK);
    }


}
