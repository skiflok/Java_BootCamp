package edu.school21.server;


import edu.school21.models.Connection;
import java.net.Socket;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
public class ServerHandler implements Runnable {

  private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);

  private final Socket socket;
  private Connection connection;
  private Connection enemyConnection;

  private final ClientConnectionPool clientConnectionPool;
  private final TrafficProxy trafficProxy;

  private final Server server;

  @SneakyThrows
  @Override
  public void run() {
    logger.info("run");
    connection = new Connection(socket);
    clientConnectionPool.addConnection(connection);

    String msg;
//    enemyConnection = clientConnectionPool.getAnotherConnection(connection);
    while (true) {
      msg = connection.receive();

      logger.info("client {} msg {}", connection.getRemoteSocketAddress(), msg);
      if ("exit".equalsIgnoreCase(msg)) {
        break;
      }

      if (clientConnectionPool.isReady()) {
        logger.info("client: {} msg: {}", connection.getRemoteSocketAddress(), msg);
        trafficProxy.proxyMessage(msg, connection);
      }
    }

    if (clientConnectionPool.getFirstPlayer().equals(connection)) {
      String statisticsJson = connection.receive();

      server.saveStatistic(connection.getStatistic(statisticsJson));

    }

  }
}
