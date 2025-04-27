package com.alxkls.eshop_backend.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class ProductDto {
  private Long id;

  private String name;
  private String brand;
  private long inventory;
  private BigDecimal price;
  private String description;

  private CategoryDto category;

  private List<ImageDto> images;
}
