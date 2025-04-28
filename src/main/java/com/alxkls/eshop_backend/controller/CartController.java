package com.alxkls.eshop_backend.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.alxkls.eshop_backend.exceptions.ResourceNotFoundException;
import com.alxkls.eshop_backend.response.ApiResponse;
import com.alxkls.eshop_backend.service.cart.CartService;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/carts")
@RequiredArgsConstructor
public class CartController {
  private final CartService cartService;

  private <T> ResponseEntity<ApiResponse> runRequest(Supplier<T> operation) {
    try {
      return ResponseEntity.ok(new ApiResponse("Success", operation.get()));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(new ApiResponse("Error", null));
    }
  }

  @GetMapping("/cart/{cartId}")
  public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId) {

    return runRequest(() -> cartService.getCart(cartId));
  }

  @DeleteMapping("/cart/{cartId}/clear")
  public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId) {
    return runRequest(
        () -> {
          cartService.clearCart(cartId);
          return null;
        });
  }

  @GetMapping("/cart/{cartId}/total-price")
  public ResponseEntity<ApiResponse> getTotalPrice(@PathVariable Long cartId) {
    return runRequest(() -> cartService.getTotalPrice(cartId));
  }
}
