package edu.school21.models.game_object;

import edu.school21.models.GameObject;
import edu.school21.models.GameObjectTypes;
import edu.school21.models.Position;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Enemy extends GameObject {


  public Enemy(String color, String symbol, Position position,
      GameObjectTypes gameObjectTypes) {
    super(color, symbol, position, gameObjectTypes);
  }
}
