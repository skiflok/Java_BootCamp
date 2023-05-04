package ex02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Program {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Неверное количество аргументов");
            return;
        }

        String path = args[0];
        FileManager fileManager = new FileManager(path);
        String input;
        String[] inputToArray;

        try (BufferedReader bis = new BufferedReader(new InputStreamReader(System.in))) {
            while (!(input = bis.readLine()).equals("exit")) {
                try {
                    inputToArray = input.split(" ");
                    switch (inputToArray[0]) {
                        case "mv":
                            fileManager.mv(inputToArray);
                            break;
                        case "ls":
                            fileManager.ls(inputToArray);
                            break;
                        case "cd":
                            fileManager.cd(inputToArray);
                            break;
                        default:
                            System.err.println("Неверная команда");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

