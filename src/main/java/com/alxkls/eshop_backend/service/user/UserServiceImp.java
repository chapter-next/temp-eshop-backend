package com.alxkls.eshop_backend.service.user;

import com.alxkls.eshop_backend.dto.UserDto;
import com.alxkls.eshop_backend.exceptions.AlreadyExistsException;
import com.alxkls.eshop_backend.exceptions.ResourceNotFoundException;
import com.alxkls.eshop_backend.model.User;
import com.alxkls.eshop_backend.repository.user.UserRepository;
import com.alxkls.eshop_backend.requests.CreateUserRequest;
import com.alxkls.eshop_backend.requests.UpdateUserRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  private final PasswordEncoder passwordEncoder;

  @Override
  public User getUser(Long userId) {
    return userRepository
        .findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
  }

  @Override
  public User createUser(CreateUserRequest createUserRequest) {
    return Optional.of(createUserRequest)
        .filter(req -> !userRepository.existsByEmail(req.getEmail()))
        .map(
            req -> {
              User user = new User();
              user.setEmail(req.getEmail());
              user.setUsername(req.getUsername());
              user.setPassword(passwordEncoder.encode(req.getPassword()));
              return userRepository.save(user);
            })
        .orElseThrow(
            () ->
                new AlreadyExistsException(
                    "Email " + createUserRequest.getEmail() + " already exists!"));
  }

  @Override
  public User updateUser(Long userId, UpdateUserRequest updateUserRequest) {

    return userRepository
        .findById(userId)
        .map(
            user -> {
              user.setUsername(updateUserRequest.getUsername());
              return userRepository.save(user);
            })
        .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
  }

  @Override
  public void deleteUser(Long userId) {

    userRepository
        .findById(userId)
        .ifPresentOrElse(
            userRepository::delete,
            () -> {
              throw new ResourceNotFoundException("User not found!");
            });
  }

  @Override
  public UserDto converToUserDto(User user) {
    return modelMapper.map(user, UserDto.class);
  }

  @Override
  public User getAuthenticatedUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();

    return userRepository.findByEmail(email);
  }
}
