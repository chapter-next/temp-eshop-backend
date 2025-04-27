package com.alxkls.eshop_backend.service.cart;

import com.alxkls.eshop_backend.exceptions.ResourceNotFoundException;
import com.alxkls.eshop_backend.model.Cart;
import com.alxkls.eshop_backend.repository.cart.CartItemRepository;
import com.alxkls.eshop_backend.repository.cart.CartRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartServiceImp implements CartService {

  private final CartRepository cartRepository;
  private final CartItemRepository cartItemRepository;

  @Override
  public Cart getCart(Long cartId) {
    Cart cart =
        cartRepository
            .findById(cartId)
            .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    cart.setTotalPrice(cart.calculateTotalPrice());

    return cartRepository.save(cart);
  }

  @Override
  @Transactional
  public void clearCart(Long cartId) {
    Cart cart =
        cartRepository
            .findById(cartId)
            .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    cartItemRepository.deleteAllByCartId(cartId);
    cart.getCartItems().clear();
    cartRepository.deleteById(cartId);
  }

  @Override
  public BigDecimal getTotalPrice(Long cartId) {

    return getCart(cartId).getTotalPrice();
  }

  @Override
  public Long initializeNewCart() {
    return cartRepository.save(new Cart()).getId();
  }
}
