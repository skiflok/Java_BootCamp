package edu.school21.services.move_game_objects_service.receivers;

import edu.school21.exceptions.MovementIsImpossibleException;
import edu.school21.models.Field;
import edu.school21.models.GameObject;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MoveCommandReceiver {

  private final Field field;

  public boolean moveUp(GameObject gameObject, GameObject target) {
    int xPosition = gameObject.getPosition().getX();
    int yPosition = gameObject.getPosition().getY();

    --yPosition;

    if (target.getPosition().getX() == xPosition && target.getPosition().getY() == yPosition) {
      return true;
    }

    swapWithEmptyField(gameObject, yPosition, xPosition);
    return false;
  }

  public boolean moveDown(GameObject gameObject, GameObject target) {
    int xPosition = gameObject.getPosition().getX();
    int yPosition = gameObject.getPosition().getY();

    ++yPosition;

    if (target.getPosition().getX() == xPosition && target.getPosition().getY() == yPosition) {
      return true;
    }

    swapWithEmptyField(gameObject, yPosition, xPosition);
    return false;
  }

  public boolean moveLeft(GameObject gameObject, GameObject target) {
    int xPosition = gameObject.getPosition().getX();
    int yPosition = gameObject.getPosition().getY();

    --xPosition;

    if (target.getPosition().getX() == xPosition && target.getPosition().getY() == yPosition) {
      return true;
    }

    swapWithEmptyField(gameObject, yPosition, xPosition);
    return false;
  }

  public boolean moveRight(GameObject gameObject, GameObject target) {
    int xPosition = gameObject.getPosition().getX();
    int yPosition = gameObject.getPosition().getY();

    ++xPosition;

    if (target.getPosition().getX() == xPosition && target.getPosition().getY() == yPosition) {
      return true;
    }

    swapWithEmptyField(gameObject, yPosition, xPosition);
    return false;
  }

  public boolean giveUp(GameObject gameObject, GameObject target) {
    System.out.println("You lose");
    System.exit(0);
    return false;
  }


  private void swapWithEmptyField(GameObject gameObject, int y, int x) {
    GameObject emptyField;

    if (field.isEmptyField(y, x)) {
      emptyField = field.getGameObject(y, x);
    } else {
      throw new MovementIsImpossibleException("Movement is not possible");
    }

    field.swapGameObject(gameObject, emptyField);
  }

}
