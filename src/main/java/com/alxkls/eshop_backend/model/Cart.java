package com.alxkls.eshop_backend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private BigDecimal totalPrice = BigDecimal.ZERO;

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CartItem> cartItems;

  public BigDecimal calculateTotalPrice() {
    return cartItems == null
        ? BigDecimal.ZERO
        : cartItems.stream()
            .map(
                item -> {
                  item.setTotalPrice();
                  return item.getTotalPrice();
                })
            .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public void addItem(CartItem cartItem) {
    cartItems.add(cartItem);
    cartItem.setCart(this);
    updateTotalPrice();
  }

  private void updateTotalPrice() {
    setTotalPrice(calculateTotalPrice());
  }

  public void removeItem(CartItem cartItem) {

    cartItems.remove(cartItem);
    cartItem.setCart(null);
    updateTotalPrice();
  }
}
