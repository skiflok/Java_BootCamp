package edu.school21.utils;

import edu.school21.models.FieldImpl;
import edu.school21.services.colorizer_service.ConsoleColorizer;
import lombok.Data;

@Data
public class FieldPrinter {

  private final FieldImpl field;
  private final ConsoleColorizer colorizer;

  public void printScene() {
    for (int y = 0; y < field.getScale(); y++) {
      for (int x = 0; x < field.getScale(); x++) {
        colorizer.printGameObject(field.getGameObject(y, x));
      }
      System.out.println();
    }
  }
}
