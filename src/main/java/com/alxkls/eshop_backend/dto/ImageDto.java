package com.alxkls.eshop_backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageDto {
  private String imageName;

  private String downloadPath;
  private Long id;
}
