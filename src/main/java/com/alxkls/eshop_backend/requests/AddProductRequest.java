package com.alxkls.eshop_backend.requests;

import com.alxkls.eshop_backend.model.Category;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class AddProductRequest {

  private Long id;
  private String name;
  private String brand;
  private long inventory;
  private BigDecimal price;
  private String description;
  private Category category;
}
