package edu.school21.controllers;

import edu.school21.models.Enemy;
import edu.school21.models.Player;
import edu.school21.models.Statistics;
import edu.school21.models.Tank;
import edu.school21.util.Connection;
import edu.school21.view.ResultsWindow;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MotionHandlerController {


  @Getter
  @FXML
  private AnchorPane rootPane;
  @FXML
  private ImageView player;

  @FXML
  private ImageView playerLife;

  @FXML
  private ImageView enemy;

  @FXML
  private ImageView enemyLife;

  @Setter
  private Connection connection;

  @Getter
  private Tank playerTank;

  @Getter
  private Tank enemyTank;

  private boolean isShowStatistic;


  public static final Logger logger = LoggerFactory.getLogger(MotionHandlerController.class);


  @FXML
  protected void initialize() {
    player.setFocusTraversable(true);
    player.requestFocus();
    enemy.setFocusTraversable(true);
    enemy.requestFocus();

    playerTank = new Player(player, playerLife, "/playerBullet.png");
    enemyTank = new Enemy(enemy, enemyLife, "/enemyBullet.png");

    rootPane.setOnKeyPressed(event -> {
      KeyCode keyCode = event.getCode();
      if (keyCode == KeyCode.LEFT) {
        logger.debug("keyCode = " + keyCode);

        sendDirection(keyCode.getName());
        playerTank.moveToTheLeft();
      } else if (keyCode == KeyCode.RIGHT) {
        logger.debug("keyCode = " + keyCode);

        sendDirection(keyCode.getName());
        playerTank.moveToTheRight();
      } else if (keyCode == KeyCode.SPACE) {
        logger.debug("keyCode = " + keyCode);

        sendDirection(keyCode.getName());
        shot(playerTank, enemyTank);
      }
    });

  }


  public void shot(Tank sender, Tank target) {
    ImageView bullet = sender.createBullet();
    sender.incrementShotsCount();

    Platform.runLater(() -> {
      rootPane.getChildren().add(bullet);
    });

    animateBullet(bullet, sender, target);
  }


  private void animateBullet(ImageView bullet, Tank sender, Tank target) {
    class TimelineWrapper {

      private Timeline animation;
    }

    TimelineWrapper wrapper = new TimelineWrapper();
    wrapper.animation = new Timeline(new KeyFrame(Duration.millis(10), event -> {

      sender.moveBullet(bullet);

      if (checkCollision(bullet, target)) {
        target.healthDecrease();

        sender.incrementHitCount();
        target.incrementBoboCount();

        if (target.isHealthOver()) {

          logger.info(sender.getClass().getName() + "  победил");

          sendDirection("exit");

          Player playerRef = (Player) playerTank;
          Enemy enemyRef = (Enemy) enemyTank;

          Statistics statistics = new Statistics(
              playerRef.getId(),
              playerRef.getShotsCount(),
              playerRef.getHitCount(),
              playerRef.getBoboCount(),
              enemyRef.getId(),
              enemyRef.getShotsCount(),
              enemyRef.getHitCount(),
              enemyRef.getBoboCount()
          );

          connection.sendStatistic(statistics);

          if (!isShowStatistic) {
            EventHandler eventHandler = new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent actionEvent) {
                ResultsWindow resultsWindow = null;
                try {
                  resultsWindow = new ResultsWindow(statistics);
                } catch (FileNotFoundException e) {
                  throw new RuntimeException(e);
                }
                if (sender.equals(playerTank)) {
                  resultsWindow.setTitle("You WIN!");
                } else {
                  resultsWindow.setTitle("You LOUSE!");
                }
                resultsWindow.show();
              }
            };
            eventHandler.handle(null);
            isShowStatistic = true;
          }
        }

        logger.info("попал по врагу");

        rootPane.getChildren().remove(bullet);
        wrapper.animation.stop();
      } else if (isBulletOutBounds(bullet)) {
        logger.info("Пуля вышла за границы");
        rootPane.getChildren().remove(bullet);
        wrapper.animation.stop();
      }
    }));

    wrapper.animation.setCycleCount(Timeline.INDEFINITE);
    wrapper.animation.play();
  }

  private boolean checkCollision(ImageView bullet, Tank target) {
    Bounds bulletBounds = bullet.getBoundsInParent();
    Bounds targetBounds = target.getTankBounds();
    return bulletBounds.intersects(targetBounds);
  }

  private boolean isBulletOutBounds(ImageView bullet) {
    double bulletY = bullet.getTranslateY();
    return Math.abs(bulletY) > rootPane.getHeight();
  }

  private void sendDirection(String direction) {
    try {
      connection.send(direction);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public String getDirection() {
    try {
      return connection.receive();
    } catch (IOException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }


}