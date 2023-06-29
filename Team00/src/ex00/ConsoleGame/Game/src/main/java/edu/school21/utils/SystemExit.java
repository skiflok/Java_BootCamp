package edu.school21.utils;

public class SystemExit {

  public static void printInSystemErrAndExit(String message) {
    System.err.println(message);
    System.exit(-1);
  }
}
