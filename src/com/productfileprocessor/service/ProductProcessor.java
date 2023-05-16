package com.productfileprocessor.service;

import com.productfileprocessor.model.Product;
import java.util.HashMap;
import java.util.Map;

public class ProductProcessor {

  private static Map<String, Integer> globalProductAmounts;
  private final Map<String, Integer> localProductAmounts = new HashMap<>();

  public ProductProcessor(Map<String, Integer> globalProductAmounts) {
    ProductProcessor.globalProductAmounts = globalProductAmounts;
  }

  public void process(Product product) {
    int amount = product.amount();
    String name = product.name();
    globalProductAmounts.compute(name, (key, value) -> value == null ? amount : value + amount);
    localProductAmounts.compute(name, (key, value) -> value == null ? amount : value + amount);
    logger(product, name);
  }

  private void logger(Product product, String name) {
    System.out.println("====================================================================");
    System.out.println("Global Amount of " + product.name() + ": " + globalProductAmounts.get(name));
    System.out.println(
        "Thread name:  " + Thread.currentThread().getName() + " Local Products Processed: " + localProductAmounts);
    System.out.println("====================================================================");
    System.out.println();
  }
}
