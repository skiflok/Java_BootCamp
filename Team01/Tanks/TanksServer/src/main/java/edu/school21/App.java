package edu.school21;

import edu.school21.config.TanksServerConfig;
import edu.school21.server.Server;
import edu.school21.util.TableInitializer;
import java.io.IOException;
import java.sql.SQLException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

  public static void main(String[] args) {
    String inputPortPattern = "--port=";
    if (args.length != 1 || !args[0].startsWith(inputPortPattern)) {
      System.err.printf("Restart server with args %sPORT%n", inputPortPattern);
      System.exit(-1);
    }

    int port = Integer.parseInt(args[0].substring(inputPortPattern.length()).trim());

    ApplicationContext context = new AnnotationConfigApplicationContext(TanksServerConfig.class);

    try {
      context.getBean(TableInitializer.class).initializeTablesWithData();
    } catch (SQLException | IOException e) {
      throw new RuntimeException(e);
    }

    Server server = context.getBean(Server.class);
    server.launch(port);

  }
}
