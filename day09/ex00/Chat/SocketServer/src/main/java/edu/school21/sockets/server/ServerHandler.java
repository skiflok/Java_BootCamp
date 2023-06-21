package edu.school21.sockets.server;

import edu.school21.sockets.models.Connection;
import edu.school21.sockets.models.User;
import edu.school21.sockets.services.UsersService;
import java.io.IOException;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerHandler {

  private static final Logger logger = LoggerFactory.getLogger(Server.class);

  private final Socket socket;
  private boolean isConnected;
  private Connection connection;
  private final UsersService usersService;


  public ServerHandler(Socket socket, UsersService usersService) {
    this.socket = socket;
    this.usersService = usersService;
  }


  public void start() throws IOException, ClassNotFoundException {
    connection = new Connection(socket);
    logger.info("Подключение клиента с удаленного адреса {}", connection.getRemoteSocketAddress());

    handShake();

    //todo singUp user

    connection.close();

  }

  private User handShake() throws IOException, ClassNotFoundException {
    String command;
    String userName = null;
    String password = null;
    User user = null;

    while (!isConnected) {

      logger.info("while");

      connection.send("Hello from Server!");
      command = connection.receive();
      logger.info("command {}", command);
      if (!"signUp".equals(command)) {
        continue;
      }

      connection.send("Enter username:");
      userName = connection.receive();
      logger.info("userName {}", userName);

      connection.send("Enter password:");
      password = connection.receive();
      logger.info("password {}", password);

      isConnected = true;

      user = new User(null, userName, password);

      usersService.signUp(user);
      connection.send("Successful!");
      logger.info("пользователь {} подключился с IP {}", userName,
          connection.getRemoteSocketAddress());
    }

    return user;
  }

}
