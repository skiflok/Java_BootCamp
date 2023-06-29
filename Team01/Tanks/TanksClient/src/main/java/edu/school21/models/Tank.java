package edu.school21.models;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

public interface Tank {

  double getCurrentPosition();

  void moveToTheLeft();

  void moveToTheRight();

  void healthDecrease();

  ImageView createBullet();

  boolean isHealthOver();

  void moveBullet(ImageView bullet);

  Bounds getTankBounds();

  void incrementShotsCount();

  void incrementHitCount();

  void incrementBoboCount();

}
