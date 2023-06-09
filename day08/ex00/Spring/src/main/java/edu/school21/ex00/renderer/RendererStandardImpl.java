package edu.school21.ex00.renderer;

import edu.school21.ex00.preProcessor.PreProcessor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RendererStandardImpl implements Renderer {

  @NonNull
  private final PreProcessor preProcessor;

  @Override
  public void print(String msg) {
    System.out.println(preProcessor.process(msg));
  }
}
