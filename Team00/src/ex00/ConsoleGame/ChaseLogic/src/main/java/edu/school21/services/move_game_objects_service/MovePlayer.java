package edu.school21.services.move_game_objects_service;

import edu.school21.exceptions.CommandNotFoundException;
import edu.school21.exceptions.MovementIsImpossibleException;
import edu.school21.models.Field;
import edu.school21.models.GameObject;
import edu.school21.services.move_game_objects_service.invokers.MoveCommandSwitch;
import edu.school21.services.move_game_objects_service.receivers.MoveCommandReceiver;
import edu.school21.utils.ConsoleHelper;


public class MovePlayer implements Movable {

  private final Field field;
  private final MoveCommandSwitch switcher;

  public MovePlayer(Field field) {
    this.field = field;
    MoveCommandReceiver receiver = new MoveCommandReceiver(field);
    switcher = new MoveCommandSwitch();
    switcher.registerCommand("w", receiver::moveUp);
    switcher.registerCommand("s", receiver::moveDown);
    switcher.registerCommand("a", receiver::moveLeft);
    switcher.registerCommand("d", receiver::moveRight);
    switcher.registerCommand("9", receiver::giveUp);
  }


  @Override
  public boolean move() {

    GameObject player = field.getPlayer();

    while (ConsoleHelper.getScanner().hasNext()) {
      String value = ConsoleHelper.readString();
      value = value.trim().toLowerCase();

      try {

        if (switcher.execute(value, player, field.getGoal())) {
          return true;
        } else {
          break;
        }

      } catch (CommandNotFoundException e) {
        System.out.println(e.getMessage());
      } catch (MovementIsImpossibleException e) {
        // TODO do something
      }

    }
    return false;

  }
}
