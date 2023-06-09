package edu.school21.ex00.printer;

import edu.school21.ex00.renderer.Renderer;
import java.time.LocalDateTime;
import lombok.Data;

@Data

public class PrinterWithDateTimeImpl implements Printer {

  private Renderer renderer;

  public void print(String msg) {
    renderer.print(LocalDateTime.now() + " " + msg);
  }
}
