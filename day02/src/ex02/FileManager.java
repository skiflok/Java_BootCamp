package ex02;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {
    private Path currentDirectory;

    public FileManager(String path) {
        this.currentDirectory = Paths.get(path);
    }
    public Path getCurrentDirectory() {
        return currentDirectory;
    }
    public void mv() {

    }
    public void ls(Path path) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
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
    public void cd() {

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
