package com.alxkls.eshop_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class OrderDto {
  private Long id;

  private LocalDate orderDateTime;
  private BigDecimal orderTotal;
  private String orderStatus;
  private List<OrderItemDto> orderItems;
}
