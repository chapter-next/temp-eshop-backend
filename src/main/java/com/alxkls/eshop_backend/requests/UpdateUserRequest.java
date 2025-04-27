package com.alxkls.eshop_backend.requests;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

@Data
public class UpdateUserRequest {
    private String username;
}
