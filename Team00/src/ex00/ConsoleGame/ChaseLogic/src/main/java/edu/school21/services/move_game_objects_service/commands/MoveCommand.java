package edu.school21.services.move_game_objects_service.commands;

import edu.school21.models.GameObject;

@FunctionalInterface
public interface MoveCommand {

  boolean move(GameObject gameObject, GameObject target);
}
