package com.productfileprocessor.service;

import com.productfileprocessor.model.Product;
import com.productfileprocessor.utils.FileReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductFileProcessorMainApp {

  private static final String DIRECTORY_PATH = "C:\\Users\\fatih\\test";
  private static final ConcurrentHashMap<String, Integer> GLOBAL_PRODUCT_AMOUNTS = new ConcurrentHashMap<>();
  private static final Map<Character, ExecutorService> THREAD_POOLS = new HashMap<>();
  private static final long POLLING_INTERVAL_MS = 1_000;

  private static final ProductProcessor productProcessor = new ProductProcessor(GLOBAL_PRODUCT_AMOUNTS);
  private static final FileReader fileReader = new FileReader();

  public static void main(String[] args) throws IOException, InterruptedException {
    initializeProcessingThreads();
    monitorDirectory();
    shutdownThreadPools();
  }

  private static void initializeProcessingThreads() {
    for (char c = 'A'; c <= 'Z'; c++) {
      ExecutorService threadPool = Executors.newSingleThreadExecutor();
      THREAD_POOLS.put(c, threadPool);
    }
  }

  private static void monitorDirectory() throws IOException, InterruptedException {
    File directory = new File(DIRECTORY_PATH);

    while (!Thread.currentThread().isInterrupted()) {
      File[] files = directory.listFiles();
      if (files != null && files.length > 0) {
        for (File file : files) {
          Product product = getProduct(file);
          char startingLetter = file.getName().toUpperCase().charAt(0);
          ExecutorService threadPool = THREAD_POOLS.get(startingLetter);
          Files.deleteIfExists(file.toPath()); // We need to remove the file prevent from duplicated process
          threadPool.submit(() -> productProcessor.process(product));
        }
      }
      Thread.sleep(POLLING_INTERVAL_MS);
    }
  }

  private static Product getProduct(File file) {
    String line = fileReader.readFileContent(file);
    String[] parts = line.split(":");
    return new Product(parts[0], Integer.parseInt(parts[1]));
  }

  private static void shutdownThreadPools() {
    for (ExecutorService threadPool : THREAD_POOLS.values()) {
      threadPool.shutdown();
    }
  }
}
