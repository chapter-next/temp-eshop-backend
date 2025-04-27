package com.alxkls.eshop_backend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class OrderItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private int quantity;
  private BigDecimal itemPrice;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product itemProduct;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order itemOrder;

  public OrderItem(Product itemProduct, Order itemOrder, int quantity, BigDecimal itemPrice) {
    this.itemProduct = itemProduct;
    this.itemOrder = itemOrder;
    this.quantity = quantity;
    this.itemPrice = itemPrice;
  }
}
