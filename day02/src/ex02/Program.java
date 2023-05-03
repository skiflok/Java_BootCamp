package ex02;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Program {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Неверное количество аргументов");
            return;
        }

        String path = args[0];
        Path directoryPath = Paths.get(path);
//        Path directoryPath = Paths.get("/Users/violator/");
        FileManager fileManager = new FileManager(path);

        String input;
        String[] inputToArray;

        try (BufferedReader bis = new BufferedReader(new InputStreamReader(System.in))) {
            while (!(input = bis.readLine()).equals("exit")) {
                inputToArray = input.split(" ");
                switch (inputToArray[0]) {
                    case "mv":
                        fileManager.mv();
                        break;
                    case "ls":
                        fileManager.ls();
                        break;
                    case "cd":
                        fileManager.cd();
                        break;
                    default:
                        System.err.println("Неверная команда");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

