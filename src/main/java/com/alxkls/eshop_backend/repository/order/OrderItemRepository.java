package com.alxkls.eshop_backend.repository.order;

import com.alxkls.eshop_backend.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {}
