package com.alxkls.eshop_backend.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.alxkls.eshop_backend.exceptions.ResourceNotFoundException;
import com.alxkls.eshop_backend.model.User;
import com.alxkls.eshop_backend.response.ApiResponse;
import com.alxkls.eshop_backend.service.cart.CartItemService;
import com.alxkls.eshop_backend.service.cart.CartService;
import com.alxkls.eshop_backend.service.user.UserService;
import com.auth0.jwt.exceptions.JWTDecodeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cart-items")
public class CartItemController {
  private final CartItemService cartItemService;
  private final CartService cartService;
  private final UserService userService;

  private <T> ResponseEntity<ApiResponse> runRequest(Runnable operation) {
    try {
      operation.run();
      return ResponseEntity.ok(new ApiResponse("Success", null));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    } catch (JWTDecodeException e){
      return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse("Unauthorized", null));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(new ApiResponse("Error", null));
    }
  }

  @PostMapping("/add-item")
  public ResponseEntity<ApiResponse> addItemToCart(@RequestParam Long productId, int quantity) {

    User user = userService.getAuthenticatedUser();

    return runRequest(
        () ->
            cartItemService.addItemToCart(
                cartService.getOrInitializeUserCart(user.getId()).getId(), productId, quantity));
  }

  @DeleteMapping("/remove-item")
  public ResponseEntity<ApiResponse> removeItemFromChart(
      @RequestParam Long cartId, @RequestParam Long productId) {
    return runRequest(() -> cartItemService.removeItemFromCart(cartId, productId));
  }

  @PutMapping("/update-quantity")
  public ResponseEntity<ApiResponse> updateItemQuantity(
      @RequestParam Long cartId, @RequestParam Long productId, int quantity) {
    return runRequest(() -> cartItemService.updateItemQuantity(cartId, productId, quantity));
  }
}
