package edu.school21.view;

import edu.school21.models.Statistics;
import java.io.FileNotFoundException;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ResultsWindow extends Stage {

  private static final int RECTANGLE_SIZE = 200;
  private static final int RECTANGLE_SIZE2 = 100;
  private static final int GAP = 3;
  private static final int WINDOW_SIZE = 3 * (RECTANGLE_SIZE2 + GAP);

  public ResultsWindow(Statistics statistics) throws FileNotFoundException {
    Pane anchorPane = new Pane();
    Rectangle rectangle1 = createRectangle(0, 0, Color.LIGHTBLUE);
    Rectangle rectangle2 = createRectangle(1, 0, Color.LIGHTBLUE);
    Rectangle rectangle3 = createRectangle(2, 0, Color.LIGHTBLUE);
    Rectangle rectangle4 = createRectangle(0, 1, Color.LIGHTBLUE);
    Rectangle rectangle5 = createRectangle(1, 1, Color.LIGHTBLUE);
    Rectangle rectangle6 = createRectangle(2, 1, Color.LIGHTBLUE);

    Text text1 = createText("playerShotsCount\n" + statistics.getPlayerShotsCount().toString(),
        rectangle1);
    Text text2 = createText("playerHitCount\n" + statistics.getPlayerHitCount().toString(),
        rectangle2);

    Text text3 = createText("playerBoboCount\n" + statistics.getPlayerBoboCount().toString(),
        rectangle3);
    Text text4 = createText("enemyShotsCount\n" + statistics.getEnemyShotsCount(), rectangle4);
    Text text5 = createText("enemyHitCount\n" + statistics.getEnemyHitCount(), rectangle5);
    Text text6 = createText("enemyBoboCount\n" + statistics.getEnemyBoboCount(), rectangle6);

    anchorPane.getChildren()
        .addAll(rectangle1, rectangle2, rectangle3, rectangle4, rectangle5, rectangle6, text1,
            text2, text3, text4, text5, text6);

    Scene scene = new Scene(anchorPane, 600, 200);

    setScene(scene);
  }

  private Rectangle createRectangle(int column, int row, Color color) {
    double x = column * (RECTANGLE_SIZE + GAP);
    double y = row * (RECTANGLE_SIZE2 + GAP);
    Rectangle rectangle = new Rectangle(x, y, RECTANGLE_SIZE, RECTANGLE_SIZE2);
    rectangle.setFill(color);
    return rectangle;
  }

  private Text createText(String content, Rectangle rectangle) {
    Text text = new Text(content);
    text.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    text.setX(rectangle.getX() + 10);
    text.setY(rectangle.getY() + 20);
    return text;
  }
}
