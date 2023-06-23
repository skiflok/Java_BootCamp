package ex01.edu.school21.sockets.server;

import static ex01.edu.school21.sockets.models.MessageType.*;

import ex01.edu.school21.sockets.models.Connection;
import ex01.edu.school21.sockets.models.Message;
import ex01.edu.school21.sockets.models.User;
import ex01.edu.school21.sockets.repositories.UsersRepository;
import ex01.edu.school21.sockets.services.UsersService;
import ex01.edu.school21.sockets.utils.ConsoleHelper;
import java.io.Console;
import java.io.IOException;
import java.net.Socket;
import java.util.Optional;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ServerHandler implements Runnable {

  private static final Logger logger = LoggerFactory.getLogger(Server.class);

  private final Socket socket;
  private boolean isConnected;
  private boolean isExit;
  private Connection connection;
  private final UsersService usersService;
  private final UsersRepository usersRepository;

  private final PasswordEncoder passwordEncoder;

  public ServerHandler(
      Socket socket,
      UsersService usersService,
      UsersRepository usersRepository,
      PasswordEncoder passwordEncoder) {
    this.socket = socket;
    this.usersService = usersService;
    this.usersRepository = usersRepository;
    this.passwordEncoder = passwordEncoder;
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
    }

    connection.close();

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
          isExit = !signIn();
          return;
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

  private boolean signIn() throws IOException, ClassNotFoundException {

    logger.info("");
    String userName;
    String password;
    Message incomeMsg;
    User user;
    boolean passCorrect = false;

    while (true) {

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

      // todo

      user = new User(null, userName, password);

      Optional<User> optionalUserFromDB = usersRepository.findByName(userName);

      if (!optionalUserFromDB.isPresent()) {
        logger.info("Неккоректный ввод userName");
        connection.send(new Message(EXIT, "Юзер не существует"));
        return false;
      }

      passCorrect = passwordEncoder.matches(password, optionalUserFromDB.get().getPassword());
      logger.info("Password match = " + passCorrect);
      logger.info("User income {}", user);
      logger.info("User DB {}", optionalUserFromDB.get());

      if (passCorrect) {
        connection.send(new Message(SIGN_IN_SUCCESS, "Log in Successful!"));
        logger.info("пользователь {} подключился к чату с IP {}", userName,
            connection.getRemoteSocketAddress());
        return true;
      }

      logger.info("Неверный пароль");
      connection.send(new Message(EXIT, "Неверный пароль"));

      return false;
    }
  }

  private void signUp() throws IOException, ClassNotFoundException {
    logger.info("");
    String userName;
    String password;
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