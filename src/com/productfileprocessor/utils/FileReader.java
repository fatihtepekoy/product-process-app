package com.productfileprocessor.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class FileReader {

  public String readFileContent(File file) {
    try (BufferedReader reader = new BufferedReader(new java.io.FileReader(file))) {
      System.out.println("Reading content of " + file.getAbsolutePath());
      return reader.readLine();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
