package com.alxkls.eshop_backend.model;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.HashSet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;

  @ManyToMany(mappedBy = "roles") private Collection<User> users = new HashSet<>();

  public Role(String name) {
    this.name = name;
  }
}
