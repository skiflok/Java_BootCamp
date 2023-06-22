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

    while (true) {
//      logger.info("startSession while");
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

  private void menuRequest() throws IOException {
    ConsoleHelper.writeMessage("Выберите пункт меню");
    String input = ConsoleHelper.readString();
    switch (input.toLowerCase().trim()) {
      case "signup":
        connection.send(new Message(SIGNUP));
        break;
      case "login":
        connection.send(new Message(LOGIN));
        break;
      case "exit":
        connection.send(new Message(EXIT));
        break;
      default:
        break;
    }
  }

  private void signUp() throws IOException, ClassNotFoundException {

    String command;
    while (!isConnected) {

      System.out.println(connection.receive().getMessage());

//      command = connection.receive();
//      ConsoleHelper.writeMessage(command);
//      logger.info("command {}", command);
//      if (!"Hello from Server!".equals(command)) {
//        continue;
//      }
//
//      connection.send(ConsoleHelper.readString());
//
//      command = connection.receive();
//      logger.info("command {}", command);
//      ConsoleHelper.writeMessage(command);
//      if (!"Enter username:".equals(command)) {
//        continue;
//      }
//      connection.send(ConsoleHelper.readString());
//
//      command = connection.receive();
//      logger.info("command {}", command);
//      ConsoleHelper.writeMessage(command);
//      if (!"Enter password:".equals(command)) {
//        continue;
//      }
//      connection.send(ConsoleHelper.readString());
//
//      command = connection.receive();
//      ConsoleHelper.writeMessage(command);
//      logger.info("command {}", command);
//      if ("Successful!".equals(command)) {
//        isConnected = true;
//      }

    }
  }

}
