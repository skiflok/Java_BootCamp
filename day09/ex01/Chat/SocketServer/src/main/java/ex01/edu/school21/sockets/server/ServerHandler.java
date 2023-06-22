package ex01.edu.school21.sockets.server;

import ex01.edu.school21.sockets.models.Connection;
import ex01.edu.school21.sockets.models.User;
import ex01.edu.school21.sockets.services.UsersService;
import java.io.IOException;
import java.net.Socket;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerHandler implements Runnable{

  private static final Logger logger = LoggerFactory.getLogger(Server.class);

  private final Socket socket;
  private boolean isConnected;
  private Connection connection;
  private final UsersService usersService;

  public ServerHandler(Socket socket, UsersService usersService) {
    this.socket = socket;
    this.usersService = usersService;
  }


  @SneakyThrows
  @Override
  public void run() {
    connection = new Connection(socket);
    logger.info("Подключение клиента с удаленного адреса {}", connection.getRemoteSocketAddress());

    while (true) {

      printMenu();
      handShake();
      connection.close();
      break;
    }



  }

  private void printMenu() throws IOException {
    connection.send("Hello from Server!");
    connection.send("Available commands:");
    connection.send("signUp");
    connection.send("logIn");
    connection.send("exit");
  }


  private void handShake() throws IOException, ClassNotFoundException {
    String command;
    String userName;
    String password;
    User user;

    while (!isConnected) {

      logger.info("while");

      connection.send("Hello from Server!");
      command = connection.receive();
      logger.info("command {}", command);
      if (!"signUp".equals(command)) {
        continue;
      }

      connection.send("Enter username:");
      userName = connection.receive();
      logger.info("userName {}", userName);

      connection.send("Enter password:");
      password = connection.receive();
      logger.info("password {}", password);

      isConnected = true;

      user = new User(null, userName, password);

      usersService.signUp(user);
      connection.send("Successful!");
      logger.info("пользователь {} подключился с IP {}", userName,
          connection.getRemoteSocketAddress());
    }

  }

}

/*
    1. Регистрация
    2. Вход (в случае, если пользователь не обнаружен - обрывать соединение).
    3. Отправка сообщения (каждый пользователь, подключенный к серверу, должен
    получить данное сообщение)
    4. Выход
*/