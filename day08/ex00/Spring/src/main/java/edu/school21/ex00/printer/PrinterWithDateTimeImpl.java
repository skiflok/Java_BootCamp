package edu.school21.ex00.printer;

import edu.school21.ex00.renderer.Renderer;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PrinterWithDateTimeImpl implements Printer {

  @NonNull
  private final Renderer renderer;

  public void print(String msg) {
    renderer.print(LocalDateTime.now() + " " + msg);
  }
}
