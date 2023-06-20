package edu.school21.sockets.client;

import edu.school21.sockets.models.Connection;
import edu.school21.sockets.utils.ConsoleHelper;
import java.net.Socket;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Data
public class Client {

  private static final Logger logger = LoggerFactory.getLogger(Client.class);

  private final int port;

  public void start() {

    try  {

      Connection connection = new Connection(new Socket("localhost", port));
      ConsoleHelper.writeMessage(connection.receive());


    } catch (Exception e) {
      logger.warn("Произошла ошибка при запуске или работе клиента {}", e.getMessage());
    }

  }

}
