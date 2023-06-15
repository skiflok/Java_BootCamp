package edu.school21.models;


import edu.school21.models.game_object.EmptyField;
import lombok.Data;

@Data
public class Field {

  private final int x;
  private final int y;


  private final GameObject[][] gameObjects;


  public Field(int x, int y) {
    this.x = x;
    this.y = y;
    gameObjects = new GameObject[y][x];
    initField();
  }

  public GameObject getObject(int y, int x) {
    return gameObjects[y][x];
  }

  private void initField() {
    for (int y = 0; y < this.y; y++) {
      for (int x = 0; x < this.x; x++) {
        gameObjects[y][x] = new EmptyField();
      }
    }
  }
}
