package edu.school21;

import edu.school21.models.Field;
import edu.school21.utils.FieldPrinter;

/**
 * Hello world!
 */
public class App {

  public static void main(String[] args) {
    System.out.println("Hello World!");

    Field field = new Field(10, 10);
    FieldPrinter fieldPrinter =new FieldPrinter(field);
    fieldPrinter.printScene();


  }
}
