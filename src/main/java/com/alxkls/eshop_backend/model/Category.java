package com.alxkls.eshop_backend.model;

import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Category {
  @OneToMany( mappedBy="category")
  List<Product> products;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;

  public Category(String name) {
    this.name = name;
  }
}
