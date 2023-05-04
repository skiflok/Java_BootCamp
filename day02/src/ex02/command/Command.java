package ex02.command;

import java.io.IOException;

@FunctionalInterface
public interface Command {
    void exec(String [] input) throws IOException;
}
