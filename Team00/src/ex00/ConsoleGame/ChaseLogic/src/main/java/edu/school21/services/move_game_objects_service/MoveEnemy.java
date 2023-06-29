package edu.school21.services.move_game_objects_service;

import edu.school21.services.move_game_objects_service.auto_movable.AutoMovableToTarget;
import edu.school21.services.move_game_objects_service.auto_movable.MovementDirection;
import java.util.List;
import edu.school21.exceptions.CommandNotFoundException;
import edu.school21.exceptions.MovementIsImpossibleException;
import edu.school21.models.Field;
import edu.school21.models.GameObject;
import edu.school21.services.move_game_objects_service.invokers.MoveCommandSwitch;
import edu.school21.services.move_game_objects_service.receivers.MoveCommandReceiver;
import edu.school21.utils.ConsoleHelper;

public class MoveEnemy implements Movable {

  private final Field field;
  private final MoveCommandSwitch switcher;

  private final AutoMovableToTarget movableToTarget;

  public MoveEnemy(Field field, AutoMovableToTarget movableToTarget) {
    this.field = field;
    this.movableToTarget = movableToTarget;
    MoveCommandReceiver receiver = new MoveCommandReceiver(field);
    switcher = new MoveCommandSwitch();
    switcher.registerCommand("w", receiver::moveUp);
    switcher.registerCommand("s", receiver::moveDown);
    switcher.registerCommand("a", receiver::moveLeft);
    switcher.registerCommand("d", receiver::moveRight);
  }


  @Override
  public boolean move() {
    List<GameObject> enemies = field.getEnemies();
    for (GameObject enemy : enemies) {

      if (field.isProfileDev()) {
        while (true) {
          try {
            if (Integer.parseInt(ConsoleHelper.readString()) == 8) {
              break;
            } else {
              throw new NumberFormatException();
            }
          } catch (NumberFormatException e) {
            System.out.println("command not found, expected 8");
          }
        }
      }

      try {
        if (switcher.execute(getNextStep(enemy), enemy, field.getPlayer())) {
          return true;
        }

        if (field.isProfileDev()) {
          field.printScene();
        }

      } catch (CommandNotFoundException e) {
        System.out.println(e.getMessage());
      } catch (MovementIsImpossibleException e) {
        System.out.println("I can't move");
      }

    }
    return false;
  }

  private String getNextStep(GameObject enemy) {
    MovementDirection movementDirection = movableToTarget.moveToTarget(enemy, field.getPlayer());
    if (movementDirection == null) {
      throw new MovementIsImpossibleException();
    }
    return movementDirection.getKeyboardButton();
  }

}
