package edu.school21;

import edu.school21.exceptions.IllegalParametersException;
import edu.school21.models.FieldImpl;
import edu.school21.models.GameObjectFactory;
import edu.school21.services.colorizer_service.ConsoleColorGameObjectPrinter;
import edu.school21.services.colorizer_service.ConsoleColorizer;
import edu.school21.services.move_game_objects_service.auto_movable.MoveToObjectBSFAlgorithm;
import edu.school21.settings.ApplicationSettings;

public class Game {

  private final FieldImpl field;

  public Game(ApplicationSettings settings) {
    ConsoleColorizer colorizer = new ConsoleColorGameObjectPrinter();
    GameObjectFactory factory = new GameObjectFactory(settings);
    field = new FieldImpl(settings, factory, colorizer);
  }

  public void start() {
    field.printScene();
    if (new MoveToObjectBSFAlgorithm(field).moveToTarget(field.getPlayer(), field.getGoal())
        == null) {
      throw new IllegalParametersException("Goal is not reachable");
    }
    while (true) {
      if (field.movePlayer()) {
        System.out.println("You win");
        break;
      }
      if (field.isProfileDev()) {
        field.printScene();
      }
      if (field.moveEnemies()) {
        System.out.println("You lose");
        break;
      }

      if (!field.isProfileDev()) {
        field.printScene();
      }
    }
  }


}
