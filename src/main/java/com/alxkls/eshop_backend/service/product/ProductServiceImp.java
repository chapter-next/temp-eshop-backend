package com.alxkls.eshop_backend.service.product;

import com.alxkls.eshop_backend.exceptions.ProductNotFoundException;
import com.alxkls.eshop_backend.model.Product;
import com.alxkls.eshop_backend.repository.product.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductServiceImp implements ProductService {

  private final ProductRepository productRepo; // using final with constructor will inject this bean

  @Override
  public void addProduct(Product product) {
  }

  @Override
  public Product getProductById(Long id) {
    return productRepo
        .findById(id)
        .orElseThrow(() -> new ProductNotFoundException("Product not found!"));
  }

  @Override
  public void deleteProductById(Long id) {
    productRepo
        .findById(id)
        .ifPresentOrElse(
            productRepo::delete,
            () -> {
              throw new ProductNotFoundException("Product now found!");
            });
  }

  @Override
  public void updateProduct(Product product, Long id) {}

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
    return productRepo.countByNameAndBrand();
  }
}
