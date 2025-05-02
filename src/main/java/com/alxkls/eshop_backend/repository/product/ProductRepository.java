package com.alxkls.eshop_backend.repository.product;

import com.alxkls.eshop_backend.model.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findAllByCategoryNameAndBrand(String categoryName, String brandName);

  List<Product> findAllByNameAndBrand(String productName, String brandName);

  List<Product> findAllByName(String productName);

  List<Product> findAllByBrand(String brandName);

  List<Product> findAllByCategoryName(String categoryName);

  Long countByNameAndBrand(String name, String brand);

  boolean existsByNameAndBrandAndDescription(String name, String brand, String description);
}
