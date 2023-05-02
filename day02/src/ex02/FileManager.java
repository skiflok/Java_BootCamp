package ex02;

import java.io.File;
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

    public void mv () {}

    public void ls (Path path) {}

    public void cd () {}

}
