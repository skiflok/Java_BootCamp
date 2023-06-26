package ex02.edu.school21.sockets.models;

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

  private static final Logger logger = LoggerFactory.getLogger(Connection.class);


  public Connection(Socket socket) throws IOException {
    this.socket = socket;
    this.out = new ObjectOutputStream(socket.getOutputStream());
    this.in = new ObjectInputStream(socket.getInputStream());
  }

  public void send(Message message) throws IOException {
    synchronized (out) {
      logger.info("user = {}", message.getUser());
      logger.info("room = {}", message.getRoom());
      logger.info(new ObjectMapper().writeValueAsString(message));
      out.writeObject(message);
    }
  }

  public Message receive () throws IOException, ClassNotFoundException {
    synchronized (in) {
      return (Message) in.readObject();
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
