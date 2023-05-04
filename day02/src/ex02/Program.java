package ex02;

import ex02.command.Command;
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
            return;
        }

        String [] inputParam = args[0].split("=");
        if (inputParam.length != 2 || !inputParam[0].equals("--current-folder")) {
            System.out.println("Неизвестнные параметры ввода");
            System.exit(-1);
        }
        String path = inputParam[1];
        FileManager fileManager = new FileManager(path);
        String input;
        String[] inputToArray;

        Command mv = new MvCommand(fileManager);
        Command ls = (stringArr) -> fileManager.ls(stringArr);
        Command cd = fileManager::cd;

        FileManagerCommandExecutor commandExecutor = new FileManagerCommandExecutor();
        commandExecutor.register("mv" , mv);
        commandExecutor.register("ls" , ls);
        commandExecutor.register("cd" , cd);

        try (BufferedReader bis = new BufferedReader(new InputStreamReader(System.in))) {
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

