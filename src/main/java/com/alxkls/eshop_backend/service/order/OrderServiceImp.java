package com.alxkls.eshop_backend.service.order;

import com.alxkls.eshop_backend.dto.OrderDto;
import com.alxkls.eshop_backend.enums.OrderStatus;
import com.alxkls.eshop_backend.exceptions.ResourceNotFoundException;
import com.alxkls.eshop_backend.model.Cart;
import com.alxkls.eshop_backend.model.Order;
import com.alxkls.eshop_backend.model.OrderItem;
import com.alxkls.eshop_backend.model.Product;
import com.alxkls.eshop_backend.repository.order.OrderRepository;
import com.alxkls.eshop_backend.repository.product.ProductRepository;
import com.alxkls.eshop_backend.service.cart.CartService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {
  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;
  private final CartService cartService;
  private final ModelMapper modelMapper;

  @Override
  public Order placeOrder(Long userId) {
    Cart userCart = cartService.getUserCart(userId);
    Order userOrder = createOrder(userCart);
    List<OrderItem> orderItems = createOrderItems(userOrder, userCart);
    userOrder.setOrderItems(new HashSet<>(orderItems));
    userOrder.setOrderTotal(getOrderTotalAmount(orderItems));
    Order savedOrder = orderRepository.save(userOrder);
    cartService.clearCart(userCart.getId());

    return savedOrder;
  }

  private Order createOrder(Cart cart) {
    Order order = new Order();
    order.setUser(cart.getUser());
    order.setOrderDateTime(LocalDate.now());
    order.setOrderStatus(OrderStatus.PENDING);
    return order;
  }

  private List<OrderItem> createOrderItems(Order order, Cart cart) {
    return cart.getCartItems().stream()
        .map(
            cartItem -> {
              Product product = cartItem.getProduct();
              product.setInventory(product.getInventory() - cartItem.getQuantity());
              productRepository.save(product);
              return new OrderItem(product, order, cartItem.getQuantity(), cartItem.getUnitPrice());
            })
        .toList();
  }

  private BigDecimal getOrderTotalAmount(List<OrderItem> orderItems) {
    return orderItems.stream()
        .map(item -> item.getItemPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  @Override
  public Order getOrder(Long orderId) {
    return orderRepository
        .findById(orderId)
        .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
  }

  @Override
  public List<Order> getUserOrders(Long userId) {
    return orderRepository.findAllByUserId(userId);
  }

  @Override
  public OrderDto convertToOrderDto(Order order) {
    return modelMapper.map(order, OrderDto.class);
  }
}
