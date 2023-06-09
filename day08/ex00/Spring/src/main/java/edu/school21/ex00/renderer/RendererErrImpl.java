package edu.school21.ex00.renderer;

import edu.school21.ex00.preProcessor.PreProcessor;
import lombok.Data;

@Data
public class RendererErrImpl implements Renderer {

  PreProcessor preProcessor;

  @Override
  public void print(String msg) {
    System.err.println(msg);
  }
}
