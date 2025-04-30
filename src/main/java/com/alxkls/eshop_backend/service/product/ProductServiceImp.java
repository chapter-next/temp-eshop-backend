package com.alxkls.eshop_backend.service.product;

import com.alxkls.eshop_backend.dto.ImageDto;
import com.alxkls.eshop_backend.dto.ProductDto;
import com.alxkls.eshop_backend.exceptions.AlreadyExistsException;
import com.alxkls.eshop_backend.exceptions.ProductNotFoundException;
import com.alxkls.eshop_backend.exceptions.ResourceNotFoundException;
import com.alxkls.eshop_backend.model.Category;
import com.alxkls.eshop_backend.model.Product;
import com.alxkls.eshop_backend.repository.category.CategoryRepository;
import com.alxkls.eshop_backend.repository.product.ProductRepository;
import com.alxkls.eshop_backend.requests.AddProductRequest;
import com.alxkls.eshop_backend.requests.UpdateProductRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductServiceImp implements ProductService {

  private final ProductRepository productRepo; // using final with constructor will inject this bean
  private final CategoryRepository categoryRepo;
  private final ModelMapper modelMapper;

  @Override
  public Product addProduct(AddProductRequest request) {

    if (productRepo.existsByName(request.getName())){
      throw new AlreadyExistsException("Product '" + request.getName() + "' already exists");
    }

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
    existingProduct.setName(
        Optional.ofNullable(request.getName()).orElse(existingProduct.getName()));
    existingProduct.setBrand(Optional.ofNullable(request.getBrand()).orElse(existingProduct.getBrand()));
    existingProduct.setPrice(Optional.ofNullable(request.getPrice()).orElse(existingProduct.getPrice()));
    existingProduct.setDescription(
        Optional.ofNullable(request.getDescription()).orElse(existingProduct.getDescription()));
    existingProduct.setInventory(Optional.ofNullable(request.getInventory()).orElse(existingProduct.getInventory()));

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

  @Override
  public ProductDto convertProductToProductDto(Product product) {
    ProductDto productDto = modelMapper.map(product, ProductDto.class);

    Optional.ofNullable(product.getImages())
        .ifPresentOrElse(
            images -> {
              productDto.setImages(
                  images.stream().map(image -> modelMapper.map(image, ImageDto.class)).toList());
            },
            () -> productDto.setImages(Collections.emptyList()));

    return productDto;
  }
}
