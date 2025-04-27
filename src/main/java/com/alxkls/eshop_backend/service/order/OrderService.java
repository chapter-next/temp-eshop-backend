package com.alxkls.eshop_backend.service.order;

import com.alxkls.eshop_backend.model.Order;

public interface OrderService {
  Order placeOrder(Long userId);

  Order getOrder(Long orderId);
}
