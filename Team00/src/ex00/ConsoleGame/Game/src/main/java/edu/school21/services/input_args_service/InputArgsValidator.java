package edu.school21.services.input_args_service;

import edu.school21.utils.SystemExit;

public class InputArgsValidator {

  private final static String PATTERN_ENEMIES_COUNT = "--enemiesCount=";
  private final static String PATTERN_WALLS_COUNT = "--wallsCount=";
  private final static String PATTERN_SIZE = "--size=";
  private final static String PATTERN_PROFILE = "--profile=";


  public static void checkInputArgs(String[] args) {
    if (args.length != 4) {
      SystemExit.printInSystemErrAndExit("Restart the app with 4 options:" + "\n1. "
          + PATTERN_ENEMIES_COUNT + "N;" + "\n2. " + PATTERN_WALLS_COUNT + "N;" + "\n3. "
          + PATTERN_SIZE + "N;" + "\n4. " + PATTERN_PROFILE + "production or dev");
    }
    if (!args[0].startsWith(PATTERN_ENEMIES_COUNT) || !args[1].startsWith(PATTERN_WALLS_COUNT)
        || !args[2].startsWith(PATTERN_SIZE) || !args[3].startsWith(PATTERN_PROFILE)) {
      SystemExit.printInSystemErrAndExit("Restart the app with 4 options:" + "\n1. "
          + PATTERN_ENEMIES_COUNT + "N;" + "\n2. " + PATTERN_WALLS_COUNT + "N;" + "\n3. "
          + PATTERN_SIZE + "N;" + "\n4. " + PATTERN_PROFILE + "production or dev");
    }
  }
}
