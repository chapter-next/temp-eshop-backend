package com.alxkls.eshop_backend.repository.cart;

import com.alxkls.eshop_backend.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

  void deleteAllByCartId(Long cartId);
}
