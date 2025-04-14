package com.alxkls.eshop_backend.service.category;

import com.alxkls.eshop_backend.exceptions.AlreadyExistsException;
import com.alxkls.eshop_backend.exceptions.ResourceNotFoundException;
import com.alxkls.eshop_backend.model.Category;
import com.alxkls.eshop_backend.repository.category.CategoryRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService {

  private final CategoryRepository categoryRepo; // injected via constructor

  @Override
  public Category getCategoryById(Long id) {
    return categoryRepo
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Cannot find category!"));
  }

  @Override
  public Category getCategoryByName(String name) {
    return categoryRepo.findByName(name);
  }

  @Override
  public Category saveCategory(Category category) {
    return Optional.of(category)
        .filter(c -> !categoryRepo.existsByName(c.getName()))
        .map(categoryRepo::save)
        .orElseThrow(
            () ->
                new AlreadyExistsException("Category '" + category.getName() + "' already exists"));
  }

  @Override
  public Category updateCategory(Category category, Long id) {

    return Optional.ofNullable(getCategoryById(id))
        .map(
            existingCategory -> {
              existingCategory.setName(category.getName());
              return categoryRepo.save(existingCategory);
            })
        .orElseThrow(() -> new ResourceNotFoundException("Cannot find category!"));
  }

  @Override
  public void deleteCategoryById(Long id) {
    categoryRepo
        .findById(id)
        .ifPresentOrElse(
            categoryRepo::delete,
            () -> {
              throw new ResourceNotFoundException("Cannot find category!");
            });
  }

  @Override
  public List<Category> getAllCategories() {
    return categoryRepo.findAll();
  }
}
