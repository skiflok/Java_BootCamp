package edu.school21.ex00.Printer;

import edu.school21.ex00.renderer.Renderer;
import edu.school21.ex00.renderer.RendererErrImpl;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrinterWithPrefixImpl implements Printer {

  Renderer renderer;
  private String prefix;

  public void print(String msg) {
    renderer = new RendererErrImpl();
    renderer.print(msg);
  }
}
