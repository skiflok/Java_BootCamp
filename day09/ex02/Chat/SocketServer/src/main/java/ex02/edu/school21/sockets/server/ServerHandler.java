package ex02.edu.school21.sockets.server;

import static ex02.edu.school21.sockets.models.MessageType.*;

import ex02.edu.school21.sockets.models.Connection;
import ex02.edu.school21.sockets.models.Message;
import ex02.edu.school21.sockets.models.User;
import ex02.edu.school21.sockets.repositories.messageRepositories.MessageRepository;
import ex02.edu.school21.sockets.repositories.userRepositories.UsersRepository;
import ex02.edu.school21.sockets.services.ActiveConnectionStorage;
import ex02.edu.school21.sockets.services.UsersService;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
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
  private boolean isChatting;
  private Connection connection;
  private final UsersService usersService;
  private final UsersRepository usersRepository;

  private final MessageRepository messageRepository;
  private final PasswordEncoder passwordEncoder;
  private final ActiveConnectionStorage activeConnectionStorage;

  private User user;

  public ServerHandler(
      Socket socket,
      UsersService usersService,
      UsersRepository usersRepository,
      MessageRepository messageRepository,
      PasswordEncoder passwordEncoder,
      ActiveConnectionStorage activeConnectionStorage) {
    this.socket = socket;
    this.usersService = usersService;
    this.usersRepository = usersRepository;
    this.messageRepository = messageRepository;
    this.passwordEncoder = passwordEncoder;
    this.activeConnectionStorage = activeConnectionStorage;
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

    activeConnectionStorage.removeUser(user.getName());
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
          isChatting = signIn();

          if (isChatting) {
            startChatting();
          }
          isExit = true;
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

  public void sendBroadcastMessage(Message message) {
    for (Connection connection : activeConnectionStorage.getConnectionList()) {
      try {
        connection.send(message);
      } catch (IOException e) {
        logger.info("Не смогли отправить сообщение {}", e.getMessage());
      }
    }
  }

  private void startChatting() throws IOException, ClassNotFoundException {
    Message msg;
    while (true) {
      logger.info("");

      msg = connection.receive();
      if (msg.getMessageType() == EXIT) {
        logger.info("Пользователь {} c IP {} отключился", user.getName(), connection.getRemoteSocketAddress());
        break;
      }
      if (msg.getMessageType() == TEXT) {
        msg.setUser(this.user);
        msg.setLocalDateTime(LocalDateTime.now());
        sendBroadcastMessage(msg);
        logger.info("this.user = {}", user);
        logger.info("LocalDateTime.now() = {}", LocalDateTime.now());
        logger.info("msg.getUser.getId = {}", msg.getUser().getId());
        messageRepository.save(msg);
      }

    }

  }

  private boolean signIn() throws IOException, ClassNotFoundException {

    logger.info("");
    String userName;
    String password;
    Message incomeMsg;
    User user;
    boolean passCorrect;

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
        this.user = user;
        this.user.setId(optionalUserFromDB.get().getId());
        activeConnectionStorage.addUser(userName, connection);
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

      try {
        logger.info(user.toString());
        logger.info(usersService.toString());
        usersService.signUp(user);
      } catch (IllegalArgumentException e) {
        logger.info(e.getMessage());
        connection.send(new Message(EXIT, "is already register!"));
        return;
      }

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