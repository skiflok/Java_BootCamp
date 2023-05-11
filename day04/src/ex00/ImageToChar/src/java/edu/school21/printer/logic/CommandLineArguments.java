package edu.school21.printer.logic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CommandLineArguments {

    public static void checkInputParam(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Неверное количество аргументов");
        }
        String white = args[0];
        String black = args[1];
        String imagePath = args[2];
        Path filePath = Paths.get(imagePath);

        if (white.length() != 1 || black.length() != 1) {
            throw new IllegalArgumentException("Неверная длина аргумента для отбражения цвета");
        }

        if (!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
            throw new IllegalArgumentException("Файл не существует");
        }
    }
}

