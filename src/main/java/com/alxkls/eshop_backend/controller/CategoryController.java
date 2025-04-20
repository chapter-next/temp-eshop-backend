package com.alxkls.eshop_backend.controller;

import static org.springframework.http.HttpStatus.*;

import com.alxkls.eshop_backend.exceptions.AlreadyExistsException;
import com.alxkls.eshop_backend.exceptions.ResourceNotFoundException;
import com.alxkls.eshop_backend.model.Category;
import com.alxkls.eshop_backend.response.ApiResponse;
import com.alxkls.eshop_backend.service.category.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
  private final CategoryService categoryService;

  @GetMapping("/all")
  public ResponseEntity<ApiResponse> getAllCategories() {
    try {
      List<Category> categories = categoryService.getAllCategories();
      return ResponseEntity.ok().body(new ApiResponse("Success", categories));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(new ApiResponse("Error", null));
    }
  }

  @PostMapping("/add")
  public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category) {
    try {
      Category savedCategory = categoryService.saveCategory(category);
      return ResponseEntity.status(CREATED).body(new ApiResponse("Success", savedCategory));
    } catch (AlreadyExistsException e) {
      return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
    }
  }

  @GetMapping("/category/{categoryId}")
  public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId) {
    try {
      Category category = categoryService.getCategoryById(categoryId);
      return ResponseEntity.ok().body(new ApiResponse("Found", category));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
  }

  @GetMapping("/category/{categoryName}") // check if it will work with getCategoryById
  public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String categoryName) {
    try {
      Category category = categoryService.getCategoryByName(categoryName);
      return ResponseEntity.ok().body(new ApiResponse("Found", category));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
  }

  @DeleteMapping("/category/{categoryId}/delete")
  public ResponseEntity<ApiResponse> deleteById(@PathVariable Long categoryId) {
    try {
      categoryService.deleteCategoryById(categoryId);
      return ResponseEntity.ok().body(new ApiResponse("Delete successful", null));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
  }

  @PutMapping("/category/{categoryId}/update")
  public ResponseEntity<ApiResponse> updateCategory(
      @PathVariable Long categoryId, @RequestBody Category category) {
    try {
      Category updatedCategory = categoryService.updateCategory(category, categoryId);
      return ResponseEntity.ok().body(new ApiResponse("Success", updatedCategory));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
  }
}
