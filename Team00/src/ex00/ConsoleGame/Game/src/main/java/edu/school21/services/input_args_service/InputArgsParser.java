package edu.school21.services.input_args_service;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Parameters(separators = "=")
public class InputArgsParser {

  @Parameter(names = "--enemiesCount")
  private int enemiesCount;

  @Parameter(names = "--wallsCount")
  private int wallsCount;

  @Parameter(names = "--size")
  private int size;

  @Parameter(names = "--profile")
  private String profile;

  public String getProfile() {
    return String.format("application-%s.properties", profile);
  }

  public void parseInputData(String[] args) {
    InputArgsValidator.checkInputArgs(args);

    JCommander.newBuilder().addObject(this).build().parse(args);
  }

}
