package edu.school21.ex00.renderer;

import edu.school21.ex00.preProcessor.PreProcessor;

public class RendererErrImpl implements Renderer {

  PreProcessor preProcessor = null;

  @Override
  public void print(String msg) {
    System.err.println(msg);
  }
}
