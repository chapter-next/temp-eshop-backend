package com.alxkls.eshop_backend.repository.product;

import com.alxkls.eshop_backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategoryNameAndBrand(String categoryName, String brandName);

    List<Product> findAllByNameAndBrand(String productName, String brandName);

    List<Product> findAllByName(String productName);

    List<Product> findAllByBrand(String brandName);

    List<Product> findAllByCategoryName(String categoryName);

    Long countByNameAndBrand(String name, String brand);
}
