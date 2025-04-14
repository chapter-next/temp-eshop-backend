package com.alxkls.eshop_backend.exceptions;

public class ProductNotFoundException extends RuntimeException {
  public ProductNotFoundException(String s) {
    super(s);
  }

  public ProductNotFoundException() {
    super();
  }
}
