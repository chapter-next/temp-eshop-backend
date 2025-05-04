package com.alxkls.eshop_backend.repository.user;

import com.alxkls.eshop_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  boolean existsByEmail(String email);

  User findByEmail(String username);
}
