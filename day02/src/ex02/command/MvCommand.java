package ex02.command;

import ex02.FileManager;

import java.io.IOException;

public class MvCommand implements Command {
    private final FileManager fileManager;

    public MvCommand(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public void exec(String [] input) throws IOException{
        fileManager.mv(input);
    }
}
