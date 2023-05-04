package ex02.command;

import ex02.FileManager;

import java.io.IOException;

public class LSCommand implements Command{

    private final FileManager fileManager;

    public LSCommand(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public void exec(String[] input) throws IOException {
        fileManager.ls(input);
    }
}
