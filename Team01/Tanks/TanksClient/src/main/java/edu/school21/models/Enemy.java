package edu.school21.models;

import edu.school21.util.Constants;
import edu.school21.controllers.MotionHandlerController;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Data;

@Data
public class Enemy implements Tank {

  private final Integer id = 2;

  private final ImageView tankImage;
  private final ImageView healthImage;
  private final String pathToBulletImage;

  private int hp = 100;

  private Integer shotsCount = 0;
  private Integer hitCount = 0;
  private Integer boboCount = 0;


  @Override
  public double getCurrentPosition() {
    return tankImage.getTranslateX();
  }

  @Override
  public void moveToTheLeft() {
    double currentPosition = getCurrentPosition();
    currentPosition -= Constants.TANK_SPEED;

    if (isInScope(currentPosition)) {
      tankImage.setTranslateX(currentPosition);
    }
  }

  @Override
  public void moveToTheRight() {
    double currentPosition = getCurrentPosition();
    currentPosition += Constants.TANK_SPEED;
    if (isInScope(currentPosition)) {
      tankImage.setTranslateX(currentPosition);
    }
  }

  @Override
  public void healthDecrease() {
    hp -= Constants.DAMAGE;
    reduceHealthImageSize();
  }

  @Override
  public ImageView createBullet() {
    ImageView bullet = new ImageView(new Image(pathToBulletImage));
    bullet.setX(tankImage.boundsInParentProperty().get().getCenterX() - 2);
    bullet.setY(
        tankImage.boundsInParentProperty().get().getCenterY() + (tankImage.getFitHeight() / 2 + 8));
    return bullet;
  }

  @Override
  public boolean isHealthOver() {
    return hp <= 0;
  }

  @Override
  public void moveBullet(ImageView bullet) {
    bullet.setTranslateY(bullet.getTranslateY() + 3);
  }

  @Override
  public Bounds getTankBounds() {
    return tankImage.getBoundsInParent();
  }


  @Override
  public void incrementShotsCount() {
    ++shotsCount;
  }

  @Override
  public void incrementHitCount() {
    ++hitCount;
  }

  @Override
  public void incrementBoboCount() {
    ++boboCount;
  }

  private boolean isInScope(double currentPosition) {
    double absPosition = Math.abs(currentPosition);
    double maxPosition = Constants.BORDERS - tankImage.getFitWidth();

    return absPosition >= 0 && absPosition <= maxPosition;
  }

  private void reduceHealthImageSize() {
    MotionHandlerController.logger.debug("ширина изображения ДО = {}", healthImage.getFitWidth());
    MotionHandlerController.logger.debug("высота изображения ДО = {}", healthImage.getFitHeight());
    double newWidth = healthImage.getFitWidth() - Constants.HP_IMAGE_REDUCTION_SIZE;

    healthImage.setPreserveRatio(false); // Отключаем сохранение пропорций
    healthImage.setFitWidth(newWidth);
    healthImage.setFitHeight(Constants.HP_IMAGE_HEIGHT);

    MotionHandlerController.logger.debug("ширина изображения ПОСЛЕ = {}",
        healthImage.getFitWidth());
    MotionHandlerController.logger.debug("высота изображения ПОСЛЕ = {}",
        healthImage.getFitHeight());
  }
}
