package edu.school21.sockets.server;

import edu.school21.sockets.models.Connection;
import edu.school21.sockets.models.User;
import java.io.IOException;
import java.net.Socket;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class ServerHandler {

  private static final Logger logger = LoggerFactory.getLogger(Server.class);

  private final Socket socket;
  private boolean isConnected;
  private Connection connection;

  public void start() throws IOException, ClassNotFoundException {
    connection = new Connection(socket);
    logger.info("Подключение клиента с удаленного адреса {}", connection.getRemoteSocketAddress());

    User user = handShake();

    //todo singUp user

    connection.close();

  }

  private User handShake() throws IOException, ClassNotFoundException {
    String command;
    String userName = null;
    String password = null;
    while (!isConnected) {

      connection.send("Hello from Server!");
      command = connection.receive();
      if (!"signUp".equals(command)) {
        continue;
      }

      connection.send("Enter username:");
      userName = connection.receive();
      connection.send("Enter password:");
      password = connection.receive();

      isConnected = true;
      connection.send("Successful!");
      logger.info("пользователь {} подключился с IP {}", userName,
          connection.getRemoteSocketAddress());
    }

    return new User(null, userName, password);
  }

}
