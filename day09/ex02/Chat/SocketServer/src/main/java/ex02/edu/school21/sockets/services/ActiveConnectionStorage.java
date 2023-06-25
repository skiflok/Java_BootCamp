package ex02.edu.school21.sockets.services;

import ex02.edu.school21.sockets.models.Connection;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ActiveConnectionStorage {

  private static ActiveConnectionStorage instance = null;

  private static final Logger logger = LoggerFactory.getLogger(ActiveConnectionStorage.class);

  private final Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

//  public Map<String, Connection> getConnectionMap() {
//    return connectionMap;
//  }

  public Collection<Connection> getConnectionList () {
    return connectionMap.values();
  }

  private ActiveConnectionStorage() {
  }


  public static ActiveConnectionStorage getInstance() {
    if (instance == null) {
      instance = new ActiveConnectionStorage();
    }
    return instance;
  }

  public void addUser (String userName, Connection connection) {
    //TODO
    connectionMap.put(userName, connection);
  }

  public void removeUser (String userName) {
    //TODO
    connectionMap.remove(userName);
  }

  @Override
  public String toString() {
    StringBuilder res = new StringBuilder();
    for (var entry: connectionMap.entrySet()) {
      res.append(entry);
      res.append("\n");
    }
    return res.toString();
  }
}