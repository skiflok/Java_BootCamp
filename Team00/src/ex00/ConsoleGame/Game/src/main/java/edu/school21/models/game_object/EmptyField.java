package edu.school21.models.game_object;

import edu.school21.models.GameObject;
import edu.school21.models.GameObjectTypes;
import edu.school21.models.Position;
import edu.school21.settings.ApplicationSettings;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmptyField extends GameObject {


  public EmptyField(String color, String symbol, Position position,
      GameObjectTypes gameObjectTypes) {
    super(color, symbol, position, gameObjectTypes);
  }

  // @Override
  // public String toString() {
  // return symbol;
  // }

}
