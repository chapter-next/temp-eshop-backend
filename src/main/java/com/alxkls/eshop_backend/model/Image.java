package com.alxkls.eshop_backend.model;

import jakarta.persistence.*;
import java.sql.Blob;
import lombok.*;

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
  private String downloadPath;

  @Lob private Blob blob;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;
}
