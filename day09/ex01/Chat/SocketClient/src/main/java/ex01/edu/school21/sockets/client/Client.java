package ex01.edu.school21.sockets.client;

import static ex01.edu.school21.sockets.models.MessageType.*;

import ex01.edu.school21.sockets.models.Connection;
import ex01.edu.school21.sockets.models.Message;
import ex01.edu.school21.sockets.utils.ConsoleHelper;
import java.io.IOException;
import java.net.Socket;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class Client {

  private static final Logger logger = LoggerFactory.getLogger(Client.class);

  private final int port;
  private boolean isConnected;
  private Connection connection;

  public void start() {

    try {

      logger.info("Check work");

      connection = new Connection(new Socket("localhost", port));
      startSession();
//      signUp();

    } catch (Exception e) {
      logger.warn("Произошла ошибка при запуске или работе клиента {}", e.getMessage());
    }

  }

  private void startSession() throws IOException, ClassNotFoundException {
    logger.info("");
    while (true) {
      Message msg = connection.receive();
      switch (msg.getMessageType()) {
        case MENU:
          ConsoleHelper.writeMessage(msg.getMessage());
          break;
        case MENU_REQUEST:
//          logger.info("MENU_REQUEST");
          menuRequest();
        default:
          break;
      }
    }
  }

  private void menuRequest() throws IOException, ClassNotFoundException {
    logger.info("menuRequest");
    ConsoleHelper.writeMessage("Выберите пункт меню");
    String input = ConsoleHelper.readString();
    switch (input.toLowerCase().trim()) {
      case "signup":
        connection.send(new Message(SIGNUP));
        signUp();
        break;
      case "login":
        connection.send(new Message(LOGIN));
        signIn();
        startChat();
        break;
      case "exit":
        connection.send(new Message(EXIT));
        break;
      default:
        break;
    }
  }

  private void startChat() throws IOException, ClassNotFoundException {
    logger.info("");

    new Thread(() -> {
      String msg;
      while (true) {
        msg = ConsoleHelper.readString();
        if ("exit".equals(msg)) {
          break;
        }
        try {
          connection.send(new Message(TEXT, msg));
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
      try {
        connection.send(new Message(EXIT));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

    }).start();

    Message msg;
    do {
      msg = connection.receive();
      ConsoleHelper.writeMessage(msg.getMessage());
    } while (msg.getMessageType() != EXIT);

  }

  private void signUp() throws IOException, ClassNotFoundException {
    logger.info("signUp");
    Message incomeMsg;

    while (!isConnected) {

      incomeMsg = connection.receive();
      if (incomeMsg.getMessageType() != NAME_REQUEST) {
        continue;
      }
      ConsoleHelper.writeMessage(incomeMsg.getMessage());
      connection.send(new Message(USER_NAME, ConsoleHelper.readString()));

      incomeMsg = connection.receive();
      if (incomeMsg.getMessageType() != PASSWORD_REQUEST) {
        continue;
      }
      ConsoleHelper.writeMessage(incomeMsg.getMessage());
      connection.send(new Message(PASSWORD, ConsoleHelper.readString()));

      incomeMsg = connection.receive();
      if (incomeMsg.getMessageType() != SIGN_UP_SUCCESS) {
        continue;
      }
      isConnected = true;
      logger.info("isConnected = true");
      ConsoleHelper.writeMessage(incomeMsg.getMessage());
    }
  }

  private void signIn() throws IOException, ClassNotFoundException {
    logger.info("LogIn");
    Message incomeMsg;

    while (true) {

      incomeMsg = connection.receive();
      if (incomeMsg.getMessageType() != NAME_REQUEST) {
        continue;
      }
      ConsoleHelper.writeMessage(incomeMsg.getMessage());
      connection.send(new Message(USER_NAME, ConsoleHelper.readString()));

      incomeMsg = connection.receive();
      if (incomeMsg.getMessageType() != PASSWORD_REQUEST) {
        continue;
      }
      ConsoleHelper.writeMessage(incomeMsg.getMessage());
      connection.send(new Message(PASSWORD, ConsoleHelper.readString()));

      incomeMsg = connection.receive();
      logger.info(incomeMsg.getMessageType().toString());
      if (incomeMsg.getMessageType() != SIGN_IN_SUCCESS && incomeMsg.getMessageType() != EXIT) {
        logger.debug("continue");
        continue;
      }

      ConsoleHelper.writeMessage(incomeMsg.getMessage());
      break;
    }
  }

}
