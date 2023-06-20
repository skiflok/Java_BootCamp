package edu.school21.sockets.server;

import edu.school21.sockets.models.Connection;
import java.io.IOException;
import java.net.Socket;
import lombok.Data;

@Data
public class ServerHandler {

  private final Socket socket;

  public void start() throws IOException, ClassNotFoundException {

    Connection connection = new Connection(socket);
    connection.send("Hello from Server!");
//    connection.receive();
    System.out.println("Start");
  }

}
