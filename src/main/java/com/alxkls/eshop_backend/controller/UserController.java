package com.alxkls.eshop_backend.controller;

import static org.springframework.http.HttpStatus.*;

import com.alxkls.eshop_backend.exceptions.AlreadyExistsException;
import com.alxkls.eshop_backend.exceptions.ResourceNotFoundException;
import com.alxkls.eshop_backend.requests.CreateUserRequest;
import com.alxkls.eshop_backend.requests.UpdateUserRequest;
import com.alxkls.eshop_backend.response.ApiResponse;
import com.alxkls.eshop_backend.service.user.UserService;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/user/{userId}")
  public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
    return runRequest(() -> userService.converToUserDto(userService.getUser(userId)));
  }

  @PostMapping("/create")
  public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
    return runRequest(() -> userService.converToUserDto(userService.createUser(createUserRequest)));
  }

  @PutMapping("/user/{userId}/update")
  public ResponseEntity<ApiResponse> updateUser(
      @PathVariable Long userId, @RequestBody UpdateUserRequest updateUserRequest) {
    return runRequest(
        () -> userService.converToUserDto(userService.updateUser(userId, updateUserRequest)));
  }

  @DeleteMapping("/user/{userId}/delete")
  public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
    return runRequest(
        () -> {
          userService.deleteUser(userId);
          return null;
        });
  }

  private <T> ResponseEntity<ApiResponse> runRequest(Supplier<T> operation) {
    try {
      return ResponseEntity.ok(new ApiResponse("Success", operation.get()));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    } catch (AlreadyExistsException e) {
      return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(new ApiResponse("Error", null));
    }
  }
}
