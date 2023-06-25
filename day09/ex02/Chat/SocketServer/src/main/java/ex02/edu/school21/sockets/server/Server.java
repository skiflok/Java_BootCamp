package ex02.edu.school21.sockets.server;

import ex02.edu.school21.sockets.config.SocketsApplicationConfig;
import ex02.edu.school21.sockets.repositories.messageRepositories.MessageRepository;
import ex02.edu.school21.sockets.repositories.userRepositories.UsersRepository;
import ex02.edu.school21.sockets.repositories.userRepositories.UsersRepositoryJdbcTemplateImpl;
import ex02.edu.school21.sockets.repositories.utils.DataBaseInitializer;
import ex02.edu.school21.sockets.services.ActiveConnectionStorage;
import ex02.edu.school21.sockets.services.UsersService;
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
  private final MessageRepository messageRepository;
  private final PasswordEncoder passwordEncoder;
  private final ActiveConnectionStorage activeConnectionStorage;

  public Server(int port) {
    this.port = port;
    ApplicationContext ctx = new AnnotationConfigApplicationContext(
        SocketsApplicationConfig.class);
//    ctx.getBean("dataBaseInitializer", DataBaseInitializer.class).init();
    usersRepository = ctx.getBean("usersRepositoryJdbcTemplateImpl",
        UsersRepositoryJdbcTemplateImpl.class);
    usersService = ctx.getBean("usersServiceImpl", UsersService.class);
    messageRepository = ctx.getBean("messageRepositoryImpl", MessageRepository.class);
    passwordEncoder = ctx.getBean("encoder", PasswordEncoder.class);
    activeConnectionStorage = ctx.getBean("activeConnectionStorage", ActiveConnectionStorage.class);
  }

  public void start() {

    try (ServerSocket serverSocket = new ServerSocket(port)) {
      logger.info("Сервер запущен на порту {}", port);

      while (true) {
        Socket socket = serverSocket.accept();
        ServerHandler serverHandler = new ServerHandler(socket,
            usersService,
            usersRepository,
            messageRepository,
            passwordEncoder,
            activeConnectionStorage);
        new Thread(serverHandler).start();
      }

    } catch (Exception e) {
      logger.warn("Произошла ошибка при запуске или работе сервера {}", e.getMessage());
      e.printStackTrace();
    }

  }
}

