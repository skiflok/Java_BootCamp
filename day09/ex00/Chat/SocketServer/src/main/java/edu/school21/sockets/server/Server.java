package edu.school21.sockets.server;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.repositories.UsersRepository;
import edu.school21.sockets.repositories.UsersRepositoryJdbcTemplateImpl;
import edu.school21.sockets.repositories.utils.DataBaseInitializer;
import edu.school21.sockets.utils.ConsoleHelper;
import java.net.ServerSocket;
import java.net.Socket;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@AllArgsConstructor
public class Server {

  private static final Logger logger = LoggerFactory.getLogger(Server.class);

  private final int port;

  public void start() {

    ApplicationContext ctx = new AnnotationConfigApplicationContext(
        SocketsApplicationConfig.class);
    ctx.getBean("dataBaseInitializer", DataBaseInitializer.class).init();
    UsersRepository usersRepository = ctx.getBean("usersRepositoryJdbcTemplateImpl",
        UsersRepositoryJdbcTemplateImpl.class);

    try (ServerSocket serverSocket = new ServerSocket(port)) {
      ConsoleHelper.writeMessage("Чат сервер запущен.");

      Socket socket = serverSocket.accept();

      new ServerHandler(socket).start();

      ConsoleHelper.writeMessage("Чат сервер запущен.");
    } catch (Exception e) {
      logger.warn("Произошла ошибка при запуске или работе сервера {}", e.getMessage());
    }

  }
}
