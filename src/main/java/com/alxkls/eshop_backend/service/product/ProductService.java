package com.alxkls.eshop_backend.service.product;

import com.alxkls.eshop_backend.dto.ProductDto;
import com.alxkls.eshop_backend.model.Product;
import com.alxkls.eshop_backend.requests.AddProductRequest;
import com.alxkls.eshop_backend.requests.UpdateProductRequest;
import java.util.List;

public interface ProductService {
  Product addProduct(AddProductRequest product);

  Product getProductById(Long id);

  void deleteProductById(Long id);

  Product updateProduct(UpdateProductRequest product, Long id);

  List<Product> getAllProducts();

  List<Product> getProductsByCategory(String categoryName);

  List<Product> getProductsByBrand(String brandName);

  List<Product> getProductsByName(String productName);

  List<Product> getProductsByNameAndBrand(String productName, String brandName);

  List<Product> getProductsByCategoryAndBrand(String categoryName, String brandName);

  Long countByNameAndBrand(String productName, String brandName);

  ProductDto convertProductToProductDto(Product product);
}
