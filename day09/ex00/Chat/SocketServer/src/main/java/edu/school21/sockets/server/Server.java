package edu.school21.sockets.server;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.repositories.UsersRepository;
import edu.school21.sockets.repositories.UsersRepositoryJdbcTemplateImpl;
import edu.school21.sockets.repositories.utils.DataBaseInitializer;
import edu.school21.sockets.services.UsersService;
import edu.school21.sockets.utils.ConsoleHelper;
import java.net.ServerSocket;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Server {

  private static final Logger logger = LoggerFactory.getLogger(Server.class);

  private final int port;

  private final UsersRepository usersRepository;
  private final UsersService usersService;


  public Server(int port) {
    this.port = port;
    ApplicationContext ctx = new AnnotationConfigApplicationContext(
        SocketsApplicationConfig.class);
    ctx.getBean("dataBaseInitializer", DataBaseInitializer.class).init();
    usersRepository = ctx.getBean("usersRepositoryJdbcTemplateImpl",
        UsersRepositoryJdbcTemplateImpl.class);
    usersService = ctx.getBean("usersServiceImpl", UsersService.class);
  }

  public void start() {

    try (ServerSocket serverSocket = new ServerSocket(port)) {
      ConsoleHelper.writeMessage("Чат сервер запущен.");

      Socket socket = serverSocket.accept();

      new ServerHandler(socket, usersService).start();

      System.out.println("All users");
      usersRepository.findAll().forEach(System.out::println);

      ConsoleHelper.writeMessage("Чат сервер остановлен.");
    } catch (Exception e) {
      logger.warn("Произошла ошибка при запуске или работе сервера {}", e.getMessage());
    }

  }
}
