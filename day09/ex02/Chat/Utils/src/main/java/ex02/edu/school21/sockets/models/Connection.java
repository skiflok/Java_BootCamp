package ex02.edu.school21.sockets.models;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@Setter
public class Connection implements Closeable {
  private final Socket socket;
  private Room room;
  private final ObjectOutputStream out;
  private final ObjectInputStream in;

  private final ObjectMapper objectMapper;

  private static final Logger logger = LoggerFactory.getLogger(Connection.class);


  public Connection(Socket socket) throws IOException {
    this.socket = socket;
    this.out = new ObjectOutputStream(socket.getOutputStream());
    this.in = new ObjectInputStream(socket.getInputStream());
    this.objectMapper = new ObjectMapper();
  }

  public void send(Message message) throws IOException {
    synchronized (out) {
      logger.info("user = {}", message.getUser());
      logger.info("room = {}", message.getRoom());
      logger.info(new ObjectMapper().writeValueAsString(message));
      out.writeObject(objectMapper.writeValueAsString(message));
//      out.writeObject(message);
    }
  }

  public Message receive () throws IOException, ClassNotFoundException {
    synchronized (in) {
      String jsonMessage = (String) in.readObject();
      logger.info(jsonMessage);
      return objectMapper.readValue(jsonMessage, Message.class);
//      return (Message) in.readObject();
    }
  }

  public SocketAddress getRemoteSocketAddress() {
    return socket.getRemoteSocketAddress();
  }

  @Override
  public void close () throws IOException {
    out.close();
    in.close();
    socket.close();
  }

}
