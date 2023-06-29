package edu.school21.view;

import edu.school21.controllers.MotionHandlerController;
import edu.school21.models.Tank;
import edu.school21.util.Connection;
import java.net.Socket;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

  public static final String SERVER_ADDRESS = "10.21.32.105";
  public static final int SERVER_PORT = 8080;

  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage stage) throws IOException {
    try {
      Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
      Connection connection = new Connection(socket);
      FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("game-view.fxml"));
      Scene scene = new Scene(fxmlLoader.load(), 750, 750);
      stage.setResizable(false);

      stage.setTitle("Tanks!");
      stage.setScene(scene);

      MotionHandlerController controller = fxmlLoader.getController();
      controller.setConnection(connection);

      stage.show();

      Tank playerTank = controller.getPlayerTank();
      Tank enemyTank = controller.getEnemyTank();

      new Thread(() -> {

        while (true) {
          String enemyDirection = controller.getDirection();

//          logger.info("direction = {}", enemyDirection);
          switch (enemyDirection.toLowerCase().trim()) {
            case "left":
              enemyTank.moveToTheLeft();
              break;
            case "right":
              enemyTank.moveToTheRight();
              break;
            case "space":
//              logger.info("root Paine = {}", controller.getRootPane());
              controller.shot(enemyTank, playerTank);
              break;
            default:
//              logger.info("Command not found {}", enemyDirection);
          }
        }


      }).start();

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}