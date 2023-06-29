package edu.school21.models.game_object;

import edu.school21.models.GameObject;
import edu.school21.models.GameObjectTypes;
import edu.school21.models.Position;

public class Wall extends GameObject {

  public Wall(String color, String symbol, Position position,
      GameObjectTypes gameObjectTypes) {
    super(color, symbol, position, gameObjectTypes);
  }
}
