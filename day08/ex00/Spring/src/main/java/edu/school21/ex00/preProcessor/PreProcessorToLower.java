package edu.school21.ex00.preProcessor;

import lombok.Data;

@Data
public class PreProcessorToLower implements PreProcessor {

  @Override
  public String process(String msg) {
    return msg.toLowerCase();
  }
}
