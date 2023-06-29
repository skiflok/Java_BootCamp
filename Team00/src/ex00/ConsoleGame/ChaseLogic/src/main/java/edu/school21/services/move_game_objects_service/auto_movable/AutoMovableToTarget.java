package edu.school21.services.move_game_objects_service.auto_movable;

import edu.school21.models.GameObject;

public interface AutoMovableToTarget {

  MovementDirection moveToTarget(GameObject who, GameObject target);

}
