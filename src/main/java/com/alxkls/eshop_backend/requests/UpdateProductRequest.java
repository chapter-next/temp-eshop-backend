package com.alxkls.eshop_backend.requests;

import com.alxkls.eshop_backend.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductRequest {
    private String name;
    private String brand;
    private long inventory;
    private BigDecimal price;
    private String description;
    private Category category;
}
