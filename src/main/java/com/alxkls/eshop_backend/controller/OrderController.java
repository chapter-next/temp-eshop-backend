package com.alxkls.eshop_backend.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.alxkls.eshop_backend.exceptions.ResourceNotFoundException;
import com.alxkls.eshop_backend.response.ApiResponse;
import com.alxkls.eshop_backend.service.order.OrderService;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {
  private final OrderService orderService;

  @PostMapping("/place/{userId}")
  public ResponseEntity<ApiResponse> placeOrder(@PathVariable Long userId) {
    return runRequest(() -> orderService.convertToOrderDto(orderService.placeOrder(userId)));
  }

  @GetMapping("/order/{orderId}")
  public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
    return runRequest(() -> orderService.convertToOrderDto(orderService.getOrder(orderId)));
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId) {

    return runRequest(
        () ->
            orderService.getUserOrders(userId).stream()
                .map(orderService::convertToOrderDto)
                .toList());
  }

  private <T> ResponseEntity<ApiResponse> runRequest(Supplier<T> operation) {
    try {
      return ResponseEntity.ok(new ApiResponse("Success", operation.get()));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(new ApiResponse("Error", null));
    }
  }
}
