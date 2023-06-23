package ex01.edu.school21.sockets.server;

import ex01.edu.school21.sockets.config.SocketsApplicationConfig;
import ex01.edu.school21.sockets.repositories.UsersRepository;
import ex01.edu.school21.sockets.repositories.UsersRepositoryJdbcTemplateImpl;
import ex01.edu.school21.sockets.repositories.utils.DataBaseInitializer;
import ex01.edu.school21.sockets.services.UsersService;
import ex01.edu.school21.sockets.utils.ConsoleHelper;
import java.net.ServerSocket;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Server {

  private static final Logger logger = LoggerFactory.getLogger(Server.class);

  private final int port;

  private final UsersService usersService;
  private final UsersRepository usersRepository;

  private final PasswordEncoder passwordEncoder;

  public Server(int port) {
    this.port = port;
    ApplicationContext ctx = new AnnotationConfigApplicationContext(
        SocketsApplicationConfig.class);
    ctx.getBean("dataBaseInitializer", DataBaseInitializer.class).init();
    usersRepository = ctx.getBean("usersRepositoryJdbcTemplateImpl",
        UsersRepositoryJdbcTemplateImpl.class);
    usersService = ctx.getBean("usersServiceImpl", UsersService.class);
    passwordEncoder = ctx.getBean("encoder", PasswordEncoder.class);
  }

  public void start() {


    try (ServerSocket serverSocket = new ServerSocket(port)) {
      logger.info("Сервер запущен на порту {}", port);

      while (true) {
        Socket socket = serverSocket.accept();
        ServerHandler serverHandler = new ServerHandler(socket,
            usersService,
            usersRepository,
            passwordEncoder);
        new Thread(serverHandler).start();
      }

    } catch (Exception e) {
      logger.warn("Произошла ошибка при запуске или работе сервера {}", e.getMessage());
      e.printStackTrace();
    }

  }
}

