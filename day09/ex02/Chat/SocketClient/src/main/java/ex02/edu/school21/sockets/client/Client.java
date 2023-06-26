package ex02.edu.school21.sockets.client;

import static ex02.edu.school21.sockets.models.MessageType.*;

import ex02.edu.school21.sockets.models.Connection;
import ex02.edu.school21.sockets.models.Message;
import ex02.edu.school21.sockets.utils.ConsoleHelper;
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
      logger.debug("Check work");
      connection = new Connection(new Socket("localhost", port));
      menu();
    } catch (Exception e) {
      logger.warn("Произошла ошибка при запуске или работе клиента {}", e.getMessage());
    }

  }

  private void menu() throws IOException, ClassNotFoundException {
    logger.debug("");
    Message msg;
    while (true) {
      msg = connection.receive();
      if (msg.getMessageType() == SUCCESS) {
        break;
      }

      ConsoleHelper.writeMessage(msg.getMessage());

      if (msg.getMessageType() == ERROR) {
        continue;
      }

      if (msg.getMessageType() == EXIT) {
        exit();
      }

      if (msg.getMessageType() == SIGN_IN_SUCCESS) {
        roomMenu();
        startChat();
      }

      connection.send(new Message(TEXT, ConsoleHelper.readString()));
    }
  }

  private void exit() throws IOException {
    connection.close();
  }



  private void roomMenu() throws IOException, ClassNotFoundException {
    logger.debug("");
    Message msg;
    while (true) {
      msg = connection.receive();
      if (msg.getMessageType() == SUCCESS) {
        break;
      }
      ConsoleHelper.writeMessage(msg.getMessage());
      connection.send(new Message(TEXT, ConsoleHelper.readString()));
    }
  }

  private void startChat() throws IOException, ClassNotFoundException {
    logger.debug("");

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
      ConsoleHelper.writeMessage(msg.getUser().getName() + ": " + msg.getMessage());
    } while (msg.getMessageType() != EXIT);

  }
}
