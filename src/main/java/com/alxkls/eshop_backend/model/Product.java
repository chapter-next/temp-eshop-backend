package com.alxkls.eshop_backend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String brand;
  private long inventory;
  private BigDecimal price;
  private String description;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "category_id")
  private Category category;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval = true)
  private List<Image> images;

  public Product(
      String name,
      String brand,
      long inventory,
      BigDecimal price,
      String description,
      Category category) {
    this.name = name;
    this.brand = brand;
    this.inventory = inventory;
    this.price = price;
    this.description = description;
    this.category = category;
  }
}
