package com.alxkls.eshop_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Image {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String fileName;
  private String fileType;
  private String downloadUrl;
  private Blob blob;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;
}
