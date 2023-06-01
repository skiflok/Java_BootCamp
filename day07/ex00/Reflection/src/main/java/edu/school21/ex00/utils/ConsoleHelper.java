package edu.school21.ex00.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class ConsoleHelper {

    private static final Logger logger = LoggerFactory.getLogger(ConsoleHelper.class);

    static {
        String inputString = "User\n" +
                "firstName\n!" +
                "lastName\n" +
                "100\n" +
                "height\n" +
                "9999\n" +
                "grow(int, double)\n";
        byte[] inputBytes = inputString.getBytes();
        InputStream inputStream = new ByteArrayInputStream(inputBytes);
//        InputStream originalInputStream = System.in;
        System.setIn(inputStream);
    }

    static private final BufferedReader bis =
            new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }
    public static void writeMessage(int message) {
        System.out.println(message);
    }

    public static void printSeparatingLine() {
        System.out.println("---------------------");
    }

    public static String readString() {
        while (true) {
            try {
                String buf = bis.readLine();
                if (buf != null)
                    return buf;
            } catch (IOException e) {
                logger.warn("Произошла ошибка при попытке ввода текста.");
            }
        }
    }

    public static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(readString().trim());
            } catch (NumberFormatException e) {
                logger.warn("Произошла ошибка при попытке ввода текста {}.", e.toString());
            }
        }
    }
}