package ex02.edu.school21.sockets.server;

import static ex02.edu.school21.sockets.models.MessageType.EXIT;
import static ex02.edu.school21.sockets.models.MessageType.MENU;
import static ex02.edu.school21.sockets.models.MessageType.MENU_REQUEST;
import static ex02.edu.school21.sockets.models.MessageType.NAME_REQUEST;
import static ex02.edu.school21.sockets.models.MessageType.PASSWORD;
import static ex02.edu.school21.sockets.models.MessageType.PASSWORD_REQUEST;
import static ex02.edu.school21.sockets.models.MessageType.SIGN_IN_SUCCESS;
import static ex02.edu.school21.sockets.models.MessageType.SIGN_UP_SUCCESS;
import static ex02.edu.school21.sockets.models.MessageType.USER_NAME;

import ex02.edu.school21.sockets.models.Connection;
import ex02.edu.school21.sockets.models.Message;
import ex02.edu.school21.sockets.models.User;
import ex02.edu.school21.sockets.services.ActiveConnectionStorage;
import ex02.edu.school21.sockets.services.UsersService;
import java.io.IOException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MenuCommand {

  private final User user;

  private final Connection connection;
  private final ActiveConnectionStorage activeConnectionStorage;
  private final UsersService usersService;

  private static final Logger logger = LoggerFactory.getLogger(MenuCommand.class);

  public MenuCommand(User user, Connection connection,
      ActiveConnectionStorage activeConnectionStorage,
      UsersService usersService) {
    this.user = user;
    this.connection = connection;
    this.activeConnectionStorage = activeConnectionStorage;

    this.usersService = usersService;
  }

  public void printMenu() throws IOException {
    logger.info("");
    connection.send(new Message(MENU,
        "Hello from Server!"
            + "\n Available commands:"
            + "\n signUp"
            + "\n logIn"
            + "\n exit"));
    connection.send(new Message(MENU_REQUEST));
  }


  public void menu() throws IOException, ClassNotFoundException {

    logger.info("func selectHandler");
    while (true) {

      connection.send(new Message(MENU_REQUEST));
      Message msg = connection.receive();
      switch (msg.getMessageType()) {
        case SIGNUP:
          logger.info(msg.getMessageType().toString());
          signUp();
          return;
        case LOGIN:
          logger.info(msg.getMessageType().toString());
//          signIn();
//          startChatting();
          return;
        case EXIT:
          connection.send(new Message(MENU, "Сервер " + "EXIT"));
          logger.info("Клиент {} отключился", connection.getRemoteSocketAddress());
          activeConnectionStorage.removeUser(user.getName());
          connection.close();
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
    String userName;
    String password;
    Message incomeMsg;
    User user;

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

//  private boolean signIn() throws IOException, ClassNotFoundException {
//
//    logger.info("");
//    String userName;
//    String password;
//    Message incomeMsg;
//    User user;
//    boolean passCorrect;
//
//    while (true) {
//
//      connection.send(new Message(NAME_REQUEST, "Enter username:"));
//      incomeMsg = connection.receive();
//      if (incomeMsg.getMessageType() != USER_NAME) {
//        continue;
//      }
//      userName = incomeMsg.getMessage();
//      logger.info("userName {}", userName);
//
//      connection.send(new Message(PASSWORD_REQUEST, "Enter password:"));
//      incomeMsg = connection.receive();
//
//      if (incomeMsg.getMessageType() != PASSWORD) {
//        continue;
//      }
//      password = incomeMsg.getMessage();
//      logger.info("password {}", password);
//
//      user = new User(null, userName, password);
//
//      Optional<User> optionalUserFromDB = usersRepository.findByName(userName);
//
//      if (!optionalUserFromDB.isPresent()) {
//        logger.info("Неккоректный ввод userName");
//        connection.send(new Message(EXIT, "Юзер не существует"));
//        return false;
//      }
//
//      passCorrect = passwordEncoder.matches(password, optionalUserFromDB.get().getPassword());
//      logger.info("Password match = " + passCorrect);
//      logger.info("User income {}", user);
//      logger.info("User DB {}", optionalUserFromDB.get());
//
//      if (passCorrect) {
//        connection.send(new Message(SIGN_IN_SUCCESS, "Log in Successful!"));
//        this.user = user;
//        this.user.setId(optionalUserFromDB.get().getId());
//        activeConnectionStorage.addUser(userName, connection);
//        logger.info("пользователь {} подключился к чату с IP {}", userName,
//            connection.getRemoteSocketAddress());
//        return true;
//      }
//
//      logger.info("Неверный пароль");
//      connection.send(new Message(EXIT, "Неверный пароль"));
//
//      return false;
//    }
//  }


}
