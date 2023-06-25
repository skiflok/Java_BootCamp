package ex02.edu.school21.sockets.utils.comand;

import java.util.HashMap;

public class CommandExecutor {

  private final HashMap<String, Command> commandMap = new HashMap<>();

  public void register(String commandName, Command command) {
    commandMap.put(commandName, command);
  }

  public void exec(String commandName)  {
    Command command = commandMap.get(commandName);
    if (command == null) {
      throw new IllegalStateException("не зарегистрирована команда для " + commandName);
    }
    command.exec();
  }


}
