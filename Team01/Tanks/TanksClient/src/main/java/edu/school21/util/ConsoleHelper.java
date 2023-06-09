package edu.school21.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ConsoleHelper {

//  private static final Logger logger
//      = LoggerFactory.getLogger(ConsoleHelper.class);

  static private final BufferedReader bis =
      new BufferedReader(new InputStreamReader(System.in));

  public static void writeMessage(String message) {
    System.out.println(message);
  }

  public static String readString() {
    while (true) {
      try {
        String buf = bis.readLine();
        if (buf != null) {
          return buf;
        }
      } catch (IOException e) {
//        logger.warn("Произошла ошибка при попытке ввода текста. Попробуйте еще раз.");
      }
    }
  }

  public static int readInt() {
    while (true) {
      try {
        return Integer.parseInt(readString().trim());
      } catch (NumberFormatException e) {
//        logger.warn("Произошла ошибка при попытке ввода текста. Попробуйте еще раз.");
      }
    }
  }

}
