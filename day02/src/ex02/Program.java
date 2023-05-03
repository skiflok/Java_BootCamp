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

        BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));

        String input;
        String [] inputToArray;
        while (true) {
            try {
                if ((input = bis.readLine()).equals("exit")) break;
                inputToArray = input.split(" ");
                switch (inputToArray[0]) {
                    case "mv" :
                        break;
                    case "ls" :
                        break;
                    case "cd" :
                        break;
                    default:
                        System.err.println("Неверная команда");
                }


            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        String path = args[0];
        Path directoryPath = Paths.get("/Users/violator/");
//        Path directoryPath = Paths.get(path);

        FileManager fileManager = new FileManager(path);

        fileManager.ls(directoryPath);


        try {
            bis.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

