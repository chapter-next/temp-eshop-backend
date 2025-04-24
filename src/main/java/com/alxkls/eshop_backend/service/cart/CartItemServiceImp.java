package com.alxkls.eshop_backend.service.cart;

import com.alxkls.eshop_backend.exceptions.ResourceNotFoundException;
import com.alxkls.eshop_backend.model.Cart;
import com.alxkls.eshop_backend.model.CartItem;
import com.alxkls.eshop_backend.model.Product;
import com.alxkls.eshop_backend.repository.cart.CartItemRepository;
import com.alxkls.eshop_backend.repository.cart.CartRepository;
import com.alxkls.eshop_backend.service.product.ProductService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImp implements CartItemService {

  private final CartRepository cartRepository;
  private final CartItemRepository cartItemRepository;
  private final ProductService productService;
  private final CartService cartService;

  @Override
  public void addItemToCart(Long cartId, Long productId, int quantity) {
    Product product = productService.getProductById(productId);
    Cart cart = cartService.getCart(cartId);
    CartItem cartItem =
        cart.getCartItems().stream()
            .filter(i -> i.getProduct().getId().equals(productId))
            .findFirst()
            .orElse(new CartItem());

    if (cartItem.getId() == null) {
      cartItem.setProduct(product);
      cartItem.setUnitPrice(product.getPrice());
      cartItem.setQuantity(quantity);
    } else {
      cartItem.setQuantity(cartItem.getQuantity() + quantity);
    }

    cartItem.setTotalPrice();
    cart.addItem(cartItem);
    cartItemRepository.save(cartItem);
    cartRepository.save(cart);
  }

  @Override
  public void removeItemFromCart(Long cartId, Long productId) {
    Cart cart = cartService.getCart(cartId);
    CartItem cartItem = getCartItem(cartId, productId);

    cart.removeItem(cartItem);

    cartItem.setTotalPrice();
    cartRepository.save(cart);
  }

  @Override
  public void updateItemQuantity(Long cartId, Long productId, int quantity) {
    Cart cart = cartService.getCart(cartId);

    cart.getCartItems().stream()
        .filter(i -> i.getProduct().getId().equals(productId))
        .findFirst()
        .ifPresent(
            i -> {
              i.setQuantity(quantity);
              i.setUnitPrice(i.getProduct().getPrice());
              i.setTotalPrice();
            });

    BigDecimal totalAmount = cart.calculateTotalPrice();
    cart.setTotalPrice(totalAmount);
    cartRepository.save(cart);
  }

  @Override
  public CartItem getCartItem(Long cartId, Long productId) {
    Cart cart = cartService.getCart(cartId);
    return cart.getCartItems().stream()
        .filter(i -> i.getProduct().getId().equals(productId))
        .findFirst()
        .orElseThrow(() -> new ResourceNotFoundException("CartItem does not exist!"));
  }
}
