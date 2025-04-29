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
  private Product product;

  @ManyToOne()
  @JoinColumn(name = "order_qid")
  private Order order;

  public OrderItem(Product product, Order order, int quantity, BigDecimal itemPrice) {
    this.product = product;
    this.order = order;
    this.quantity = quantity;
    this.itemPrice = itemPrice;
  }
}
