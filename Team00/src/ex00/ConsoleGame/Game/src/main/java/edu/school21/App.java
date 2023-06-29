package edu.school21;

import edu.school21.services.input_args_service.InputArgsParser;
import edu.school21.settings.ApplicationSettings;
import edu.school21.utils.SystemExit;


public class App {

  public static void main(String[] args) {

    try {
      InputArgsParser inputDataParser = new InputArgsParser();
      inputDataParser.parseInputData(args);

      ApplicationSettings settings = new ApplicationSettings(inputDataParser);

      Game game = new Game(settings);
      game.start();
    } catch (Exception e) {
      SystemExit.printInSystemErrAndExit(e.getMessage());
    }

  }
}
