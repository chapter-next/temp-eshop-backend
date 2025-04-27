package com.alxkls.eshop_backend.repository.order;

import com.alxkls.eshop_backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
  List<Order> findAllByUserId(Long userId);
}
