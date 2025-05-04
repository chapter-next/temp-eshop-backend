package com.alxkls.eshop_backend.repository.role;

import com.alxkls.eshop_backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
  boolean existsByName(String role);

  Role findByName(String admin);
}
