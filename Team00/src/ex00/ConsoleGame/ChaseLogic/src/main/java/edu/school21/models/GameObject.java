package edu.school21.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class GameObject {

  protected String color;
  protected String symbol;
  protected Position position;
  protected GameObjectTypes gameObjectTypes;

  public String toString() {
    return symbol;
  }

}
