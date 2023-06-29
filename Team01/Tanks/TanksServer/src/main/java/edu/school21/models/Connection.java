package edu.school21.models;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Connection implements Closeable {

  private final Socket socket;
  private final ObjectOutputStream out;
  private final ObjectInputStream in;

  @EqualsAndHashCode.Include
  private final UUID uuid;

//  private final ObjectMapper objectMapper;

  private static final Logger logger = LoggerFactory.getLogger(Connection.class);


  public Connection(Socket socket) throws IOException {
    this.socket = socket;
    this.out = new ObjectOutputStream(socket.getOutputStream());
    this.in = new ObjectInputStream(socket.getInputStream());
    uuid = UUID.randomUUID();
  }

  public void send(String message) throws IOException {
    synchronized (out) {
      logger.debug(message);
      out.writeObject(message);
    }
  }

  public void sendStatistic(Statistics statistics) {
    try {
      send(new ObjectMapper().writeValueAsString(statistics));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public Statistics getStatistic(String statisticsJson) {
    try {
      return new ObjectMapper().readValue(statisticsJson, Statistics.class);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public String receive() throws IOException, ClassNotFoundException {
    synchronized (in) {
      return (String) in.readObject();
    }
  }

  public SocketAddress getRemoteSocketAddress() {
    return socket.getRemoteSocketAddress();
  }

  @Override
  public void close() throws IOException {
    out.close();
    in.close();
    socket.close();
  }

}
