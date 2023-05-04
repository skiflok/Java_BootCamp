package ex02;

import ex02.command.Command;

import java.io.IOException;
import java.util.HashMap;

public class FileManagerCommandExecutor {
    private final HashMap<String, Command> commandMap = new HashMap<>();

    public void register(String commandName, Command command) {
        commandMap.put(commandName, command);
    }

    public void exec(String commandName, String [] input) throws IOException {
        Command command = commandMap.get(commandName);
        if (command == null) {
            throw new IllegalStateException("не зарегистрирована команда для " + commandName);
        }
        command.exec(input);
    }
}
