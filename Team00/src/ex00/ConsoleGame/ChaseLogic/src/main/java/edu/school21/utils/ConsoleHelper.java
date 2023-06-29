package edu.school21.utils;

import java.util.Scanner;

public class ConsoleHelper {

  private static final Scanner scanner = new Scanner(System.in);

  public static Scanner getScanner() {
    return scanner;
  }

  public static String readString() {
    return scanner.nextLine().trim();
  }

}
