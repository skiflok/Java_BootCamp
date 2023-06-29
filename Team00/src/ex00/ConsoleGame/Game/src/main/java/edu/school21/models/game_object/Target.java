package edu.school21.models.game_object;

import edu.school21.models.GameObject;
import edu.school21.models.GameObjectTypes;
import edu.school21.models.Position;

public class Target extends GameObject {


  public Target(String color, String symbol, Position position,
      GameObjectTypes gameObjectTypes) {
    super(color, symbol, position, gameObjectTypes);
  }
}
