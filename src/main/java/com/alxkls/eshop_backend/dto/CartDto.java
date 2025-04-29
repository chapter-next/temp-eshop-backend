package com.alxkls.eshop_backend.dto;

import com.alxkls.eshop_backend.model.CartItem;
import com.alxkls.eshop_backend.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data

public class CartDto {
    private Long id;

    private BigDecimal totalPrice;

    private Set<CartItemDto> cartItems;

}


