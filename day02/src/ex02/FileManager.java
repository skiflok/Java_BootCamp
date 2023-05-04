package ex02;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {
    private Path currentDirectory;

    public FileManager(String path) {
        Path p = Paths.get(path);
        if (Files.exists(p) && Files.isDirectory(p)) {
            this.currentDirectory = p;
        } else {
            System.err.println("Введенная директория не валидна");
            System.exit(-1);
        }
    }
    public Path getCurrentDirectory() {
        return currentDirectory;
    }
    public void mv(String[] inputToArray) {
        if (inputToArray.length != 3) throw new IllegalArgumentException("Неверное количество аргументов для команды mv");


    }
    public void ls(String[] inputToArray) {
        if (inputToArray.length != 1) throw new IllegalArgumentException("Неверное количество аргументов для команды ls");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(currentDirectory)) {
            for (Path file : stream) {
                if (Files.isDirectory(file)) {
                    System.out.printf("%s %d KB\n", file.getFileName(), getDirectorySize(file) / 1024);
                } else {
                    System.out.printf("%s %d KB\n", file.getFileName(), Files.size(file) / 1024);
                }
            }
        } catch (IOException e) {
            System.err.printf("Ошибка обращения к дериктории %s \n",e.getMessage());
        }
    }
    public void cd(String[] inputToArray) {
        if (inputToArray.length != 2) throw new IllegalArgumentException("Неверное количество аргументов для команды cd");
    }

    public long getDirectorySize(Path path) {
        long totalSize = 0;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path file : stream) {
                if (Files.isDirectory(file)) {
                    totalSize += getDirectorySize(file);
                } else {
                    totalSize += Files.size(file);
                }
            }
        } catch (IOException ignore) {
        }
        return totalSize;
    }
}
