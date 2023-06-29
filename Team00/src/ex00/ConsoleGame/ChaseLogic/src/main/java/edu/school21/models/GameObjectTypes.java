package edu.school21.models;

import lombok.Getter;

@Getter
public enum GameObjectTypes {
  EMPTY("empty"), ENEMY("enemy"), GOAL("goal"), PLAYER("player"), WALL("wall");

  private final String charType;
  private final String colorType;


  GameObjectTypes(String type) {
    this.charType = type + ".char";
    this.colorType = type + ".color";
  }
}
