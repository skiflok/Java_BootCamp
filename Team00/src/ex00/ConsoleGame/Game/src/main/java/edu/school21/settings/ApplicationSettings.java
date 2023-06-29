package edu.school21.settings;

import edu.school21.exceptions.IllegalParametersException;
import edu.school21.models.GameObjectTypes;
import edu.school21.services.input_args_service.InputArgsParser;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import edu.school21.App;
import edu.school21.utils.SystemExit;
import lombok.Data;

@Data
public class ApplicationSettings {

  private final Properties properties = new Properties();
  private final int enemiesCount;
  private final int wallsCount;
  private final int size;
  private final String profile;


  public ApplicationSettings(InputArgsParser inputProperties) {
    enemiesCount = inputProperties.getEnemiesCount();
    wallsCount = inputProperties.getWallsCount();
    size = inputProperties.getSize();
    profile = inputProperties.getProfile();

    if ((enemiesCount + wallsCount + 2) >= size * size) {
      throw new IllegalParametersException("Too many objects");
    }

    try (InputStream input =
        App.class.getClassLoader().getResourceAsStream(inputProperties.getProfile())) {

      properties.load(input);

    } catch (IOException e) {
      SystemExit.printInSystemErrAndExit(e.getMessage());
    }
  }

  public String getObjectColor(GameObjectTypes obj) {
    return properties.getProperty(obj.getColorType());
  }

  public String getObjectSymbol(GameObjectTypes obj) {
    return properties.getProperty(obj.getCharType());
  }


}
