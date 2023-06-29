package edu.school21.services.move_game_objects_service.invokers;

import java.util.HashMap;
import java.util.Map;
import edu.school21.exceptions.CommandNotFoundException;
import edu.school21.models.GameObject;
import edu.school21.services.move_game_objects_service.commands.MoveCommand;

public class MoveCommandSwitch {

  private final Map<String, MoveCommand> commands = new HashMap<>();

  public void registerCommand(String commandName, MoveCommand command) {
    commands.put(commandName, command);
  }

  public boolean execute(String commandName, GameObject gameObject, GameObject target) {
    MoveCommand command = commands.get(commandName);

    if (command == null) {
      throw new CommandNotFoundException("Command not found " + commandName);
    }
    return command.move(gameObject, target);
  }
}

