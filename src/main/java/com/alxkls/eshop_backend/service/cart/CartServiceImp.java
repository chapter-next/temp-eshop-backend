package com.alxkls.eshop_backend.service.cart;

import com.alxkls.eshop_backend.exceptions.ResourceNotFoundException;
import com.alxkls.eshop_backend.model.Cart;
import com.alxkls.eshop_backend.model.User;
import com.alxkls.eshop_backend.repository.cart.CartItemRepository;
import com.alxkls.eshop_backend.repository.cart.CartRepository;
import com.alxkls.eshop_backend.service.user.UserService;
import java.math.BigDecimal;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartServiceImp implements CartService {

  private final CartRepository cartRepository;
  private final CartItemRepository cartItemRepository;
  private final UserService userService;

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

  @Override
  public Cart getUserCart(Long userId) {
    return cartRepository.findByUserId(userId);
  }

  @Override
  public Cart getOrInitializeUserCart(Long userId) {
    User user = userService.getUser(userId);
    return Optional.ofNullable(cartRepository.findByUserId(user.getId()))
        .orElseGet(
            () -> {
              Cart cart = new Cart();
              cart.setUser(user);
              return cartRepository.save(cart);
            });
  }
}
