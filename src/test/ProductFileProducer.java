package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class ProductFileProducer {

  private static final String DIRECTORY_PATH = "C:\\Users\\fatih\\test";
  private static final Random RANDOM = new Random();
  private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

  // The product processor should count it orange 10.000 and apple 10.000
  private static final int LOOP_COUNT = 1000;
  private static final int AMOUNT = 10;

  public static void main(String[] args) {
    Thread threadA = new Thread(() -> createFilesWithPrefix("Orange", AMOUNT));
    Thread threadB = new Thread(() -> createFilesWithPrefix("Apple", AMOUNT));

    threadA.start();
    threadB.start();
  }

  private static void createFilesWithPrefix(String product, int amount) {
    for (int i = 1; i <= LOOP_COUNT; i++) {
      String filename = getRandomChar() + i + ".txt";
      String content = product + ":" + amount;
      createFile(filename, content);
    }
  }

  private static void createFile(String filename, String content) {
    try {
      File file = new File(DIRECTORY_PATH + File.separator + filename);
      FileWriter fileWriter = new FileWriter(file);
      BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
      bufferedWriter.write(content);
      bufferedWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static String getRandomChar() {
    return String.valueOf(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())))
        + ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length()))
        + ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length()))
        + ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length()))
        + ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length()));
  }
}
