package com.alxkls.eshop_backend.repository.order;

import com.alxkls.eshop_backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {}
