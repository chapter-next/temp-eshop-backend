package com.alxkls.eshop_backend.service.product;

import com.alxkls.eshop_backend.exceptions.ProductNotFoundException;
import com.alxkls.eshop_backend.exceptions.ResourceNotFoundException;
import com.alxkls.eshop_backend.model.Category;
import com.alxkls.eshop_backend.model.Product;
import com.alxkls.eshop_backend.repository.category.CategoryRepository;
import com.alxkls.eshop_backend.repository.product.ProductRepository;
import com.alxkls.eshop_backend.requests.AddProductRequest;
import com.alxkls.eshop_backend.requests.UpdateProductRequest;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductServiceImp implements ProductService {

  private final ProductRepository productRepo; // using final with constructor will inject this bean
  private final CategoryRepository categoryRepo;

  @Override
  public Product addProduct(AddProductRequest request) {
    Category category =
        Optional.ofNullable(categoryRepo.findByName(request.getCategory().getName()))
            .orElseGet(() -> categoryRepo.save(new Category(request.getCategory().getName())));

    return productRepo.save(createProductFromRequest(request, category));
  }

  private Product createProductFromRequest(AddProductRequest request, Category category) {
    return new Product(
        request.getName(),
        request.getBrand(),
        request.getInventory(),
        request.getPrice(),
        request.getDescription(),
        category);
  }

  @Override
  public Product getProductById(Long id) {
    return productRepo
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
  }

  @Override
  public void deleteProductById(Long id) {
    productRepo
        .findById(id)
        .ifPresentOrElse(
            productRepo::delete,
            () -> {
              throw new ResourceNotFoundException("Product now found!");
            });
  }

  @Override
  public Product updateProduct(UpdateProductRequest request, Long id) {

    return productRepo
        .findById(id)
        .map(existingProduct -> updateExistingProductUsingRequest(existingProduct, request))
        .map(productRepo::save)
        .orElseThrow(() -> new ProductNotFoundException("Product not found!"));
  }

  private Product updateExistingProductUsingRequest(
      Product existingProduct, UpdateProductRequest request) {
    existingProduct.setName(request.getName());
    existingProduct.setBrand(request.getBrand());
    existingProduct.setPrice(request.getPrice());
    existingProduct.setDescription(request.getDescription());
    existingProduct.setInventory(request.getInventory());

    Category category = categoryRepo.findByName(request.getCategory().getName());
    existingProduct.setCategory(category);

    return existingProduct;
  }

  @Override
  public List<Product> getAllProducts() {
    return productRepo.findAll();
  }

  @Override
  public List<Product> getProductsByCategory(String categoryName) {
    return productRepo.findAllByCategoryName(categoryName);
  }

  @Override
  public List<Product> getProductsByBrand(String brandName) {
    return productRepo.findAllByBrand(brandName);
  }

  @Override
  public List<Product> getProductsByName(String productName) {
    return productRepo.findAllByName(productName);
  }

  @Override
  public List<Product> getProductsByNameAndBrand(String productName, String brandName) {
    return productRepo.findAllByNameAndBrand(productName, brandName);
  }

  @Override
  public List<Product> getProductsByCategoryAndBrand(String categoryName, String brandName) {
    return productRepo.findAllByCategoryNameAndBrand(categoryName, brandName);
  }

  @Override
  public Long countByNameAndBrand(String productName, String brandName) {
    return productRepo.countByNameAndBrand(productName, brandName);
  }
}
