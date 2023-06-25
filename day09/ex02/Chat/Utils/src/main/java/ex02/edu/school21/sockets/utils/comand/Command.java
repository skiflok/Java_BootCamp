package ex02.edu.school21.sockets.utils.comand;

import java.io.IOException;

@FunctionalInterface
public interface Command {
    void exec() throws IOException;
}
