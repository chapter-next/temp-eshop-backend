package com.alxkls.eshop_backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class ImageDto {
  private String fileName;
  private String downloadPath;
  private Long id;
}
