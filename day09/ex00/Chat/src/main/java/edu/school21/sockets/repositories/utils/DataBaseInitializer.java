package edu.school21.sockets.repositories.utils;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Statement;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@PropertySource("classpath:db.properties")
public class DataBaseInitializer {

  private final DataSource ds;

  @Value("${db.init.schema.path}")
  String schemaPath;
  @Value("${db.init.data.path}")
  String dataPath;

  @Autowired
  public DataBaseInitializer(@Qualifier("hikariDataSource") DataSource ds) {
    this.ds = ds;
  }

  public void init() {
    try (Statement statement = ds.getConnection().createStatement()) {

      String sql = Files.lines(Paths.get(schemaPath).normalize().toAbsolutePath()).collect(Collectors.joining("\n"));
      String data = Files.lines(Paths.get(dataPath).normalize().toAbsolutePath()).collect(Collectors.joining("\n"));
      System.out.println(sql);
      System.out.println(data);
      statement.executeUpdate(sql);
      statement.executeUpdate(data);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
