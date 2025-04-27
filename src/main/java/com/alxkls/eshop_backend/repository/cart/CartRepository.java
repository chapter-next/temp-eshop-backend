package com.alxkls.eshop_backend.repository.cart;

import com.alxkls.eshop_backend.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);
}
