package edu.school21.ex00.printer;

import edu.school21.ex00.renderer.Renderer;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PrinterWithPrefixImpl implements Printer {

  @NonNull
  private final Renderer renderer;

  private String prefix;

  public void print(String msg) {
    renderer.print(prefix + " " + msg);
  }
}
