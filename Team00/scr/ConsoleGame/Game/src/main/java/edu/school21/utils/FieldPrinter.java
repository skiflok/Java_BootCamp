package edu.school21.utils;

import edu.school21.models.Field;
import lombok.Data;

@Data
public class FieldPrinter {

  private final Field field;

  public void printScene() {
    for (int y = 0; y < field.getY(); y++) {
      for (int x = 0; x < field.getX(); x++) {
        System.out.print(field.getObject(y, x).toString());
      }
      System.out.println();
    }
  }

}
