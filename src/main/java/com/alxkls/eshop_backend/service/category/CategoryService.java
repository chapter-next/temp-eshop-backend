package com.alxkls.eshop_backend.service.category;

import com.alxkls.eshop_backend.model.Category;
import java.util.List;

public interface CategoryService {
  Category getCategoryById(Long id);

  Category getCategoryByName(String name);

  Category saveCategory(Category category);

  Category updateCategory(Category category, Long id);

  void deleteCategoryById(Long id);

  List<Category> getAllCategories();
}
