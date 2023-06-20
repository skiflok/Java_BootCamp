package edu.school21.sockets.server;

import java.net.Socket;
import lombok.Data;

@Data
public class ServerHandler {

  private final Socket socket;

  public void start() {}

}
