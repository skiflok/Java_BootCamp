package edu.school21.models;


import static edu.school21.models.GameObjectTypes.*;

import edu.school21.services.colorizer_service.ConsoleColorizer;
import edu.school21.services.move_game_objects_service.Movable;
import edu.school21.services.move_game_objects_service.MoveEnemy;
import edu.school21.services.move_game_objects_service.MovePlayer;
import edu.school21.services.move_game_objects_service.auto_movable.MoveToObjectBSFAlgorithm;
import edu.school21.settings.ApplicationSettings;
import java.util.ArrayList;
import java.util.List;
import edu.school21.services.RandomGenerator;
import lombok.Data;

@Data
public class FieldImpl implements Field {

  private final Movable movePlayer = new MovePlayer(this);
  private final Movable moveEnemy = new MoveEnemy(this, new MoveToObjectBSFAlgorithm(this));
  private final int scale;
  private final GameObject[][] gameObjects;
  private final ApplicationSettings settings;
  private final GameObjectFactory factory;
  private final ConsoleColorizer colorizer;

  private GameObject player;
  private GameObject goal;
  private List<GameObject> enemies = new ArrayList<>();
  private List<GameObject> walls = new ArrayList<>();

  public FieldImpl(ApplicationSettings settings, GameObjectFactory factory,
      ConsoleColorizer colorizer) {
    this.settings = settings;
    this.scale = settings.getSize();
    this.factory = factory;
    this.colorizer = colorizer;
    gameObjects = new GameObject[scale][scale];
    initFieldWithEmptyFields();
    spawnGameObject();
  }

  @Override
  public List<GameObject> getEnemies() {
    return enemies;
  }

  @Override
  public GameObject getPlayer() {
    return player;
  }

  @Override
  public GameObject getGoal() {
    return goal;
  }

  @Override
  public GameObject getGameObject(int y, int x) {
    return gameObjects[y][x];
  }

  @Override
  public boolean isEmptyField(int y, int x) {
    if (y >= scale || y < 0 || x >= scale || x < 0) {
      return false;
    }
    return (gameObjects[y][x].getGameObjectTypes() == EMPTY);
  }

  @Override
  public void swapGameObject(GameObject first, GameObject second) {
    int xFirst = first.getPosition().getX();
    int yFirst = first.getPosition().getY();

    int xSecond = second.getPosition().getX();
    int ySecond = second.getPosition().getY();

    gameObjects[yFirst][xFirst] = second;
    gameObjects[ySecond][xSecond] = first;

    first.getPosition().setX(xSecond);
    first.getPosition().setY(ySecond);

    second.getPosition().setX(xFirst);
    second.getPosition().setY(yFirst);
  }

  @Override
  public boolean isProfileDev() {
    return settings.getProfile().equals("application-dev.properties");
  }

  @Override
  public void printScene() {
    if (!isProfileDev()) {
      printLineBreak(150);
    }

    for (int y = 0; y < scale; y++) {
      for (int x = 0; x < scale; x++) {
        colorizer.printGameObject(gameObjects[y][x]);
      }
      System.out.println();
    }
  }


  private void initFieldWithEmptyFields() {
    for (int y = 0; y < scale; y++) {
      for (int x = 0; x < scale; x++) {
        gameObjects[y][x] = factory.createGameObject(EMPTY, new Position(x, y));
      }
    }
  }

  public void spawnGameObject() {
    generateGameObject(PLAYER, 1);
    generateGameObject(GOAL, 1);
    generateGameObject(ENEMY, settings.getEnemiesCount());
    generateGameObject(WALL, settings.getWallsCount());
  }

  private void generateGameObject(GameObjectTypes type, int count) {
    int x;
    int y;

    while (count != 0) {
      x = RandomGenerator.getRandomArray(scale);
      y = RandomGenerator.getRandomArray(scale);
      if (gameObjects[y][x].getGameObjectTypes() == EMPTY) {
        gameObjects[y][x] = factory.createGameObject(type, new Position(x, y));

        if (type == PLAYER) {
          player = gameObjects[y][x];
        } else if (type == GOAL) {
          goal = gameObjects[y][x];
        } else if (type == ENEMY) {
          enemies.add(gameObjects[y][x]);
        } else if (type == WALL) {
          walls.add(gameObjects[y][x]);
        }

        count--;

      }
    }
  }


  public boolean movePlayer() {
    return movePlayer.move();
  }

  public boolean moveEnemies() {
    return moveEnemy.move();
  }


  private void printLineBreak(int count) {
    for (int i = 0; i < count; i++) {
      System.out.println();
    }
  }

}
