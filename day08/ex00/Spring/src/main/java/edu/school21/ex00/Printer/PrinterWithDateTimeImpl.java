package edu.school21.ex00.Printer;

import edu.school21.ex00.renderer.Renderer;
import edu.school21.ex00.renderer.RendererErrImpl;
import java.time.LocalDateTime;

public class PrinterWithDateTimeImpl implements Printer{

  Renderer renderer;

  public void print(String msg) {
    renderer = new RendererErrImpl();
    renderer.print(LocalDateTime.now() + " " + msg);
  }
}
