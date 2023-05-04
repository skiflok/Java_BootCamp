package ex02;

import java.io.IOException;
import java.nio.file.*;

public class FileManager {
    private Path currentDirectory;

    public FileManager(String path) {
        Path p = Paths.get(path);
        if (Files.exists(p) && Files.isDirectory(p)) {
            this.currentDirectory = p.toAbsolutePath().normalize();
        } else {
            System.err.println("Введенная директория не валидна");
            System.exit(-1);
        }
    }

    public void mv(String[] inputToArray) throws IOException {
        if (inputToArray.length != 3)
            throw new IllegalArgumentException("Неверное количество аргументов для команды mv");
        Path source = currentDirectory.resolve(Paths.get(inputToArray[1]));
        Path target = currentDirectory.resolve(Paths.get(inputToArray[2]));
        if (Files.isDirectory(target)) {
            Files.move(source, target.resolve(source.getFileName()));
        } else {
            Files.move(source, target);
        }
    }

    public void ls(String[] inputToArray) {
        if (inputToArray.length != 1)
            throw new IllegalArgumentException("Неверное количество аргументов для команды ls");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(currentDirectory)) {
            for (Path file : stream) {
                if (Files.isDirectory(file)) {
                    System.out.printf("%s %d KB\n", file.getFileName(), getDirectorySize(file) / 1024);
                } else {
                    System.out.printf("%s %d KB\n", file.getFileName(), Files.size(file) / 1024);
                }
            }
        } catch (IOException e) {
            System.err.printf("Ошибка обращения к дериктории %s \n", e.getMessage());
        }
    }

    public void cd(String[] inputToArray) throws NotDirectoryException {
        if (inputToArray.length != 2)
            throw new IllegalArgumentException("Неверное количество аргументов для команды cd");
        if (!Files.isDirectory(currentDirectory.resolve(Paths.get(inputToArray[1])))) throw new NotDirectoryException("Неверная директория");
        currentDirectory = currentDirectory.resolve(Paths.get(inputToArray[1])).normalize();
        System.out.println(currentDirectory);
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
