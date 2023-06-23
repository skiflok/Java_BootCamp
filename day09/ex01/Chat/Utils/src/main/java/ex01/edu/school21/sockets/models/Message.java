package ex01.edu.school21.sockets.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {

  private final MessageType messageType;
  private final String message;
  private final User user;
  private final LocalDateTime localDateTime;


  public Message(MessageType messageType) {
    this.messageType = messageType;
    message = null;
  }

  public Message(MessageType messageType, String message) {
    this.messageType = messageType;
    this.message = message;
  }

  public Message(User id, String text, String password) {

  }

  public MessageType getMessageType() {
    return messageType;
  }

  public String getMessage() {
    return message;
  }
}