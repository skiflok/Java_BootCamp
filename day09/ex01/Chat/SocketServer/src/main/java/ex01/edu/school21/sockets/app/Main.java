package ex01.edu.school21.sockets.app;


import ex01.edu.school21.sockets.server.Server;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {

    if (args.length != 1) {
      logger.error("Неверное количество параметров для запуска сервера");
      System.exit(-1);
    }

    String[] params = args[0].split("=");

    logger.debug("String[] args = {}", Arrays.toString(params));

    if (!"--server-port".equals(params[0])) {
      logger.error("Неверные параметры запуска");
      System.exit(-1);
    }

    try {
      int port = Integer.parseInt(params[1]);
      Server server = new Server(port);
      server.start();

    } catch (NumberFormatException e) {
      logger.error("Введите корректный порт");
    }

  }
}
