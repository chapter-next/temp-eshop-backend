package com.alxkls.eshop_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private int quantity;

  private BigDecimal unitPrice;
  private BigDecimal totalPrice;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @JsonIgnore
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "cart_id")
  private Cart cart;

  public void setTotalPrice() {
    this.totalPrice = this.unitPrice.multiply(BigDecimal.valueOf(quantity));
  }
}
