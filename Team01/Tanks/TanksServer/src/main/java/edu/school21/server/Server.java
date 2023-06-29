package edu.school21.server;

import edu.school21.services.StatisticsService;
import edu.school21.models.Statistics;
import java.net.ServerSocket;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Server {

  private static final Logger logger = LoggerFactory.getLogger(Server.class);

  @Autowired
  private StatisticsService service;
  @Autowired
  private ClientConnectionPool clientConnectionPool;
  @Autowired
  private TrafficProxy trafficProxy;


  public void launch(int port) {

    try (ServerSocket serverSocket = new ServerSocket(port)) {
      logger.info("Сервер запущен на порту {}", port);


        while (!clientConnectionPool.isReady()) {
          logger.info("synchronized");
          Socket socket = serverSocket.accept();
          ServerHandler serverHandler = new ServerHandler(socket, clientConnectionPool, trafficProxy, this);
          new Thread(serverHandler).start();
          Thread.sleep(500);

        }
        logger.info("while exit");

        synchronized (this) {
          wait();
        }


      logger.info("server exit");


    } catch (Exception e) {
//      logger.warn("Произошла ошибка при запуске или работе сервера {}", e.getMessage());
      e.printStackTrace();
    }
  }

  public synchronized void saveStatistic(Statistics entity) {
    service.saveStatistic(entity);
  }

  public synchronized void updateStatistic(Statistics entity) {
    service.updateStatistic(entity);
  }

}
