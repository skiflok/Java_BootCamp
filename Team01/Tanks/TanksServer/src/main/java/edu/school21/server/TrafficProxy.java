package edu.school21.server;

import edu.school21.models.Connection;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrafficProxy {
  @Autowired
  private final ClientConnectionPool clientConnectionPool;

  public void proxyMessage(String msg, Connection connection) throws IOException {
    clientConnectionPool.getAnotherConnection(connection).send(msg);
  }

}
