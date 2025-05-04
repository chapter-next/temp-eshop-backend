package com.alxkls.eshop_backend.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthNRequest {
  @NotBlank private String email;
  @NotBlank private String password;
}
