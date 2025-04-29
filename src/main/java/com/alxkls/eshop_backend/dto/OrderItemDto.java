package com.alxkls.eshop_backend.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderItemDto {
  private Long id;

  private int quantity;
  private BigDecimal itemPrice;

  private ProductDto product;
}
