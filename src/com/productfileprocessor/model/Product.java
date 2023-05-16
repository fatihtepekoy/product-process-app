package com.productfileprocessor.model;

public record Product(String name, int amount) {

  @Override
  public String toString() {
    return "Product{" +
        "name='" + name + '\'' +
        ", amount=" + amount +
        '}';
  }
}
