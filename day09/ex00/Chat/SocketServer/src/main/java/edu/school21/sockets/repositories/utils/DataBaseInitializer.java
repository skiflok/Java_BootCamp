package edu.school21.sockets.repositories.utils;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Statement;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@PropertySource("classpath:db.properties")
public class DataBaseInitializer {

  private static final Logger logger
      = LoggerFactory.getLogger(DataBaseInitializer.class);

  private final DataSource ds;

  @Value("${db.init.schema.path}")
  String schemaPath;
  @Value("${db.init.data.path}")
  String dataPath;

  @Autowired
  public DataBaseInitializer(DataSource ds) {
    this.ds = ds;
  }

  public void init() {

    try (Statement statement = ds.getConnection().createStatement()) {
      logger.debug("Директория запуска {}", Paths.get("./").toAbsolutePath().normalize());

      String sql = Files.lines(Paths.get(schemaPath).normalize().toAbsolutePath()).collect(Collectors.joining("\n"));
      String data = Files.lines(Paths.get(dataPath).normalize().toAbsolutePath()).collect(Collectors.joining("\n"));
      logger.debug("sql\n {}", sql);
      logger.debug("data\n {}", data);
      statement.executeUpdate(sql);
      statement.executeUpdate(data);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
