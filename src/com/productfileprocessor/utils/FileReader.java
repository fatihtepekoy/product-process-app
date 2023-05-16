package com.productfileprocessor.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileReader {

  public String readFileContent(File file) {
    try {
      System.out.println("Reading content of " + file.getAbsolutePath());
      return Files.readAllLines(file.toPath()).get(0);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

}
