package com.alxkls.eshop_backend.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse { // does it make sense to use Records instead for this?
    private String message;
    private Object data;
}

