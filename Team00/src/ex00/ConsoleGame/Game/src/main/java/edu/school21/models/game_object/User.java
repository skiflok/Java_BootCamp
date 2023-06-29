package edu.school21.models.game_object;

import edu.school21.models.GameObject;
import edu.school21.models.GameObjectTypes;
import edu.school21.models.Position;

public class User extends GameObject {

  public User(String color, String symbol, Position position,
      GameObjectTypes gameObjectTypes) {
    super(color, symbol, position, gameObjectTypes);
  }

}
