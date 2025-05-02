package com.alxkls.eshop_backend.service.user;

import com.alxkls.eshop_backend.dto.UserDto;
import com.alxkls.eshop_backend.model.User;
import com.alxkls.eshop_backend.requests.CreateUserRequest;
import com.alxkls.eshop_backend.requests.UpdateUserRequest;

public interface UserService {
  User getUser(Long userId);

  User createUser(CreateUserRequest createUserRequest);

  User updateUser(Long userId, UpdateUserRequest updateUserRequest);

  void deleteUser(Long userId);

  UserDto converToUserDto(User user);

  User getAuthenticatedUser();
}
