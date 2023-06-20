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

      Connection connection = new Connection(new Socket("localhost", port));
      handShake();

    } catch (Exception e) {
      logger.warn("Произошла ошибка при запуске или работе клиента {}", e.getMessage());
    }

  }

  private void handShake() throws IOException, ClassNotFoundException {

    String command;
    while (!isConnected) {

      command = connection.receive();

      if (!"Hello from Server!".equals(command)) {
        continue;
      }

      command = connection.receive();
      if (!"Enter username:".equals(command)) {
        continue;
      }
      connection.send(ConsoleHelper.readString());

      command = connection.receive();
      command = connection.receive();
      if (!"Enter password:".equals(command)) {
        continue;
      }
      connection.send(ConsoleHelper.readString());

      command = connection.receive();
      if (!"Successful!".equals(command)) {
        continue;
      } else {
        isConnected = true;
      }
    }
  }

}
