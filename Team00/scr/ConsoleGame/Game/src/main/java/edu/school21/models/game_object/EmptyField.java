package edu.school21.models.game_object;

import edu.school21.models.GameObject;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmptyField extends GameObject {

  {
    symbol = ".";
  }

  @Override
  public String toString() {
    return symbol;
  }

}
