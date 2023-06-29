package edu.school21.models;

import java.util.List;

public interface Field {

  List<GameObject> getEnemies();

  GameObject getPlayer();

  GameObject getGoal();

  GameObject getGameObject(int y, int x);

  boolean isEmptyField(int y, int x);

  void swapGameObject(GameObject first, GameObject second);

  boolean isProfileDev();

  void printScene();

  int getScale();

}
