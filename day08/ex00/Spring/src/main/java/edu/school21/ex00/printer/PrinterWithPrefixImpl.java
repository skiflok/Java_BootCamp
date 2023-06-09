package edu.school21.ex00.printer;

import edu.school21.ex00.renderer.Renderer;
import lombok.Data;

@Data
public class PrinterWithPrefixImpl implements Printer {

  Renderer renderer;
  private String prefix;

  public void print(String msg) {
    renderer.print(prefix + " " + msg);
  }
}
