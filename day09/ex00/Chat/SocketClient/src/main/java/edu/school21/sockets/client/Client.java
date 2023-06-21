package edu.school21.sockets.client;

import edu.school21.sockets.models.Connection;
import edu.school21.sockets.utils.ConsoleHelper;
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

    try  {

      logger.info("Check work");

      connection = new Connection(new Socket("localhost", port));
      handShake();

    } catch (Exception e) {
      logger.warn("Произошла ошибка при запуске или работе клиента {}", e.getMessage());
    }

  }

  private void handShake() throws IOException, ClassNotFoundException {

    String command;
    while (!isConnected) {

      logger.info("wile");

      command = connection.receive();
      ConsoleHelper.writeMessage(command);
      logger.info("command {}", command);
      if (!"Hello from Server!".equals(command)) {
        continue;
      }

      connection.send(ConsoleHelper.readString());

      command = connection.receive();
      logger.info("command {}", command);
      ConsoleHelper.writeMessage(command);
      if (!"Enter username:".equals(command)) {
        continue;
      }
      connection.send(ConsoleHelper.readString());

      command = connection.receive();
      logger.info("command {}", command);
      ConsoleHelper.writeMessage(command);
      if (!"Enter password:".equals(command)) {
        continue;
      }
      connection.send(ConsoleHelper.readString());

      command = connection.receive();
      ConsoleHelper.writeMessage(command);
      logger.info("command {}", command);
      if ("Successful!".equals(command)) {
        isConnected = true;
      }
    }
  }

}

/*
Hello from Server!
> signUp
Enter username:
> Marsel
Enter password:
> qwerty007
Successful!
* */