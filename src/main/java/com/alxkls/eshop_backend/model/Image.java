package com.alxkls.eshop_backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.type.UserComponentType;
import org.hibernate.type.descriptor.java.BlobJavaType;


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

  @Lob
  private Blob blob;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;
}
