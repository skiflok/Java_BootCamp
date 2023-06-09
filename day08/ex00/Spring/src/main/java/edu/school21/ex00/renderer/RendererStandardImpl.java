package edu.school21.ex00.renderer;

import edu.school21.ex00.preProcessor.PreProcessor;

public class RendererStandardImpl implements Renderer{

  PreProcessor preProcessor;

  @Override
  public void print(String msg) {
    System.out.println(msg);
  }
}
