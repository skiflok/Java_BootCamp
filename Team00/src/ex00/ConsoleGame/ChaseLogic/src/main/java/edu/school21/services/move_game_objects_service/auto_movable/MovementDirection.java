package edu.school21.services.move_game_objects_service.auto_movable;

import lombok.Getter;

@Getter
public enum MovementDirection {
  UP("w"),
  DOWN("s"),
  LEFT("a"),
  RIGHT("d");
  private final String keyboardButton;

  MovementDirection(String keyboardButton) {
    this.keyboardButton = keyboardButton;
  }
}
