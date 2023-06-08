package edu.school21.ex00.Printer;

import edu.school21.ex00.renderer.Renderer;
import java.time.LocalDateTime;

public class PrinterWithDateTimeImpl implements Printer{

  Renderer renderer;

  public void print(String msg) {
    System.out.println(LocalDateTime.now());
  }
}
