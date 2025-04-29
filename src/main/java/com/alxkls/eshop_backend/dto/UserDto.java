package com.alxkls.eshop_backend.dto;

import com.alxkls.eshop_backend.model.Cart;
import java.util.List;
import lombok.Data;

@Data
public class UserDto {
  private Long id;

  private String username;
  private String email;
  //    private String password;

    private List<OrderDto> orders;
    private CartDto cart;
}
