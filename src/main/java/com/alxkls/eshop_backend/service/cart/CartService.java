package com.alxkls.eshop_backend.service.cart;

import com.alxkls.eshop_backend.dto.CartDto;
import com.alxkls.eshop_backend.model.Cart;
import java.math.BigDecimal;

public interface CartService {
  Cart getCart(Long cartId);

  void clearCart(Long cartId);

  BigDecimal getTotalPrice(Long cartId);

  Long initializeNewCart();

  Cart getUserCart(Long userId);

  Cart getOrInitializeUserCart(Long userId);

  CartDto convertToCArtDto(Cart cart);
}
