package ex01.edu.school21.sockets.server;

import static ex01.edu.school21.sockets.models.MessageType.*;

import ex01.edu.school21.sockets.models.Connection;
import ex01.edu.school21.sockets.models.Message;
import ex01.edu.school21.sockets.models.User;
import ex01.edu.school21.sockets.services.UsersService;
import java.io.IOException;
import java.net.Socket;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerHandler implements Runnable {

  private static final Logger logger = LoggerFactory.getLogger(Server.class);

  private final Socket socket;
  private boolean isConnected;
  private boolean isExit;
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

    while (!isExit) {

      logger.info("");
      printMenu();
      selectHandler();
//      connection.close();
//      break;
    }


  }

  private void printMenu() throws IOException {
    logger.info("");
    connection.send(new Message(MENU, "Hello from Server!"));
    connection.send(new Message(MENU, "Available commands:"));
    connection.send(new Message(MENU, "signUp"));
    connection.send(new Message(MENU, "logIn"));
    connection.send(new Message(MENU, "exit"));
    connection.send(new Message(MENU_REQUEST));
  }

  private void selectHandler() throws IOException, ClassNotFoundException {

    logger.info("func selectHandler");
    while (true) {

      Message msg = connection.receive();
      switch (msg.getMessageType()) {
        case SIGNUP:
          logger.info(msg.getMessageType().toString());
          connection.send(new Message(MENU, "Сервер " + "SIGNUP"));
          signUp();
          return;
        case LOGIN:
          logger.info(msg.getMessageType().toString());
          connection.send(new Message(MENU, "Сервер " + "LOGIN"));
          break;
        case EXIT:
          connection.send(new Message(MENU, "Сервер " + "EXIT"));
          logger.info("Клиент {} отключился", connection.getRemoteSocketAddress());
          connection.close();
          isExit = true;
          return;
        default:
          logger.info(msg.getMessageType().toString());
          connection.send(new Message(MENU_REQUEST, "Неверная команда"));
          printMenu();
          break;
      }
    }
  }


  private void signUp() throws IOException, ClassNotFoundException {
    logger.info("");
    String userName = null;
    String password = null;
    Message incomeMsg;
    User user;

    while (!isConnected) {

      connection.send(new Message(NAME_REQUEST, "Enter username:"));
      incomeMsg = connection.receive();
      if (incomeMsg.getMessageType() != USER_NAME) {
        continue;
      }
      userName = incomeMsg.getMessage();
      logger.info("userName {}", userName);

      connection.send(new Message(PASSWORD_REQUEST, "Enter password:"));
      incomeMsg = connection.receive();

      if (incomeMsg.getMessageType() != PASSWORD) {
        continue;
      }
      password = incomeMsg.getMessage();
      logger.info("password {}", password);

      isConnected = true;

      user = new User(null, userName, password);
      usersService.signUp(user);
      connection.send(new Message(SIGN_UP_SUCCESS, "Successful!"));
      logger.info("пользователь {} зарегистрировался с IP {}", userName,
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