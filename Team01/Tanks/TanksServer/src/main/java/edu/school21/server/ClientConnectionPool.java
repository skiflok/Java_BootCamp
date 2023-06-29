package edu.school21.server;

import edu.school21.models.Connection;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ClientConnectionPool {

  private static final Logger logger = LoggerFactory.getLogger(ClientConnectionPool.class);

  private final List<Connection> connections = new ArrayList<>(2);

  public synchronized void addConnection (Connection connection) {
    connections.add(connection);
    logger.info("size {}", connections.size());
  }

  public Connection getFirstPlayer() {
    return connections.get(0);
  }


  public Connection getSecondPlayer() {
    return connections.get(1);
  }

  public Connection getAnotherConnection(Connection connection) {
    return connection.equals(getFirstPlayer()) ? getSecondPlayer() : getFirstPlayer();
  }

  public synchronized boolean isReady () {
    return connections.size() == 2;
  }

  public int getSize () {return connections.size();}

}
