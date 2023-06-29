package edu.school21.services.move_game_objects_service.auto_movable;

import static edu.school21.services.move_game_objects_service.auto_movable.MovementDirection.*;

import edu.school21.models.Field;
import edu.school21.models.GameObject;
import lombok.Data;

@Data
public class EnemyAutoMovableToTargetImpl implements AutoMovableToTarget {

  private final Field field;

  @Override
  public MovementDirection moveToTarget(GameObject who, GameObject target) {
    return simpleMove(who, target);
  }

  private MovementDirection simpleMove(GameObject who, GameObject target) {

    int deltaX = who.getPosition().getX() - target.getPosition().getX();
    int deltaY = who.getPosition().getY() - target.getPosition().getY();

    return Math.abs(deltaX) >= Math.abs(deltaY) ? horizontalMove(deltaX) : verticalMove(deltaY);

  }

  private MovementDirection horizontalMove(int deltaX) {
    return deltaX > 0 ? LEFT : RIGHT;
  }

  private MovementDirection verticalMove(int deltaY) {
    return deltaY > 0 ? UP : DOWN;
  }

}
