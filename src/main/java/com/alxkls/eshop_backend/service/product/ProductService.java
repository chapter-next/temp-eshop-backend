package com.alxkls.eshop_backend.service.product;

import com.alxkls.eshop_backend.exceptions.ProductNotFoundException;
import com.alxkls.eshop_backend.model.Product;
import java.util.List;

public interface ProductService {
  void addProduct(Product product);

  Product getProductById(Long id);

  void deleteProductById(Long id);

  void updateProduct(Product product, Long id);

  List<Product> getAllProducts();

  List<Product> getProductsByCategory(String categoryName);

  List<Product> getProductsByBrand(String brandName);

  List<Product> getProductsByName(String productName);
  List<Product> getProductsByNameAndBrand(String productName, String brandName);

  List<Product> getProductsByCategoryAndBrand(String categoryName, String brandName);

  Long countByNameAndBrand(String productName, String brandName);
}
