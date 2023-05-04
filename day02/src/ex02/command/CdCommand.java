package ex02.command;

import ex02.FileManager;

import java.io.IOException;

public class CdCommand implements Command{

    private final FileManager fileManager;

    public CdCommand(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public void exec(String[] input) throws IOException {
        fileManager.cd(input);
    }
}
