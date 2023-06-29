package edu.school21.models;

import edu.school21.models.game_object.EmptyField;
import edu.school21.models.game_object.Enemy;
import edu.school21.models.game_object.Target;
import edu.school21.models.game_object.User;
import edu.school21.models.game_object.Wall;
import edu.school21.settings.ApplicationSettings;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GameObjectFactory {

  private final ApplicationSettings settings;

  public GameObject createGameObject(GameObjectTypes type, Position position) {

    GameObject gameObject;
    String color = settings.getObjectColor(type);
    String symbol = settings.getObjectSymbol(type);

    switch (type) {
      case EMPTY:
        gameObject = new EmptyField(color, symbol, position, type);
        break;
      case ENEMY:
        gameObject = new Enemy(color, symbol, position, type);
        break;
      case GOAL:
        gameObject = new Target(color, symbol, position, type);
        break;
      case PLAYER:
        gameObject = new User(color, symbol, position, type);
        break;
      case WALL:
        gameObject = new Wall(color, symbol, position, type);
        break;
      default:
        throw new IllegalArgumentException("There is no such object");
    }

    return gameObject;
  }

}
