package com.alxkls.eshop_backend.requests;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

@Data
public class CreateUserRequest {
    private String username;
    private String email;
    private String password;
}
