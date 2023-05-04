package ex02;

import ex02.command.MvCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystemException;
import java.nio.file.NoSuchFileException;

public class Program {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Неверное количество аргументов");
            System.exit(-1);
        }

        String[] inputParam = args[0].split("=");
        if (inputParam.length != 2 || !inputParam[0].equals("--current-folder")) {
            System.out.println("Неизвестнные параметры ввода");
            System.exit(-1);
        }

        FileManager fileManager = new FileManager(inputParam[1]);
        FileManagerCommandExecutor commandExecutor = new FileManagerCommandExecutor();

        commandExecutor.register("mv", new MvCommand(fileManager));
        commandExecutor.register("ls", (stringArr) -> fileManager.ls(stringArr));
        commandExecutor.register("cd", fileManager::cd);

        try (BufferedReader bis = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            String[] inputToArray;
            while (!(input = bis.readLine()).equals("exit")) {
                try {
                    inputToArray = input.split(" ");
                    commandExecutor.exec(inputToArray[0], inputToArray);
                } catch (IllegalArgumentException | IllegalStateException e) {
                    System.err.println(e.getMessage());
                } catch (NoSuchFileException e) {
                    System.err.printf("Файл %s не найден\n", e.getMessage());
                } catch (FileSystemException e) {
                    System.out.println("Ошибка " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
