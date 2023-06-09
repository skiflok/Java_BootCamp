package edu.school21.ex00.renderer;

import edu.school21.ex00.preProcessor.PreProcessor;
import lombok.Data;

@Data
public class RendererStandardImpl implements Renderer {

  PreProcessor preProcessor;

  @Override
  public void print(String msg) {
    System.out.println(preProcessor.process(msg));
  }
}
