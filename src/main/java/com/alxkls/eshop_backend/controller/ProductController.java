package com.alxkls.eshop_backend.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.alxkls.eshop_backend.exceptions.ProductNotFoundException;
import com.alxkls.eshop_backend.exceptions.ResourceNotFoundException;
import com.alxkls.eshop_backend.model.Product;
import com.alxkls.eshop_backend.requests.AddProductRequest;
import com.alxkls.eshop_backend.requests.UpdateProductRequest;
import com.alxkls.eshop_backend.response.ApiResponse;
import com.alxkls.eshop_backend.service.product.ProductService;
import java.util.List;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController {

  private final ProductService productService;

  @GetMapping("/all")
  public ResponseEntity<ApiResponse> getAllProducts() {
    return runFindByRequest(productService::getAllProducts);
  }

  @GetMapping("find-name")
  public ResponseEntity<ApiResponse> getByName(@RequestParam String productName) {
    return runFindByRequest(() -> productService.getProductsByName(productName));
  }

  @GetMapping("find-brand")
  public ResponseEntity<ApiResponse> getByBrand(@RequestParam String brandName) {
    return runFindByRequest(() -> productService.getProductsByBrand(brandName));
  }

  @GetMapping("find-name-brand")
  public ResponseEntity<ApiResponse> getByBrandAndName(
      @RequestParam String productName, @RequestParam String brandName) {
    return runFindByRequest(() -> productService.getProductsByNameAndBrand(productName, brandName));
  }

  @GetMapping("find-category")
  public ResponseEntity<ApiResponse> getByCategory(@RequestParam String categoryName) {
    return runFindByRequest(() -> productService.getProductsByCategory(categoryName));
  }

  @GetMapping("find-category-brand")
  public ResponseEntity<ApiResponse> getByCategoryAndBrand(
      @RequestParam String categoryName, @RequestParam String brandName) {
    return runFindByRequest(
        () -> productService.getProductsByCategoryAndBrand(categoryName, brandName));
  }

  @PostMapping("add")
  public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest addProductRequest) {
    return runRequest(() -> productService.addProduct(addProductRequest));
  }

  @GetMapping("product/{productId}")
  public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
    return runRequest(() -> productService.getProductById(productId));
  }

  @PutMapping("product/{productId}/update")
  public ResponseEntity<ApiResponse> updateProduct(
      @PathVariable Long productId, @RequestBody UpdateProductRequest updateProductRequest) {
    return runRequest(() -> productService.updateProduct(updateProductRequest, productId));
  }

  @DeleteMapping("product/{productId}/delete")
  public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {
    try {
      productService.deleteProductById(productId);
      return ResponseEntity.ok(new ApiResponse("Success", null));
    } catch (ProductNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Not Found", null));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(new ApiResponse("Error", null));
    }
  }

  public ResponseEntity<ApiResponse> countByBrandAndName(
      @RequestParam String productName, @RequestParam String brandName) {
    try {
      var count = productService.countByNameAndBrand(productName, brandName);
      return ResponseEntity.ok(new ApiResponse("Success", count));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(new ApiResponse("Error", null));
    }
  }

  private ResponseEntity<ApiResponse> runFindByRequest(Supplier<List<Product>> operation) {
    try {
      List<Product> products = operation.get();
      if (products.isEmpty()) {
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Not Found", null));
      }
      return ResponseEntity.ok().body(new ApiResponse("Success", products));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(new ApiResponse("Error", null));
    }
  }

  private ResponseEntity<ApiResponse> runRequest(Supplier<Product> operation) {
    try {
      Product theProduct = operation.get();
      return ResponseEntity.ok(new ApiResponse("Success", theProduct));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Not Found", null));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(new ApiResponse("Error", null));
    }
  }
}
