package edu.school21.services.colorizer_service;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import edu.school21.models.GameObject;

public class ConsoleColorGameObjectPrinter implements ConsoleColorizer {

  @Override
  public void printGameObject(GameObject gameObject) {

    String color = gameObject.getColor();

    ColoredPrinter coloredPrinter =
        new ColoredPrinter.Builder(1, false)
            .foreground(Ansi.FColor.valueOf(color)).build();

    coloredPrinter.print(gameObject.toString());
  }

}
