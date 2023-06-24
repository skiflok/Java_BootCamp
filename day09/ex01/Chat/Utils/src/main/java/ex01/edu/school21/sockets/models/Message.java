package ex01.edu.school21.sockets.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Data;


@Data
public class Message implements Serializable {

  private Long id;
  private User user;
  private String message;
  private LocalDateTime localDateTime;
  private transient final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
  private MessageType messageType;

  public Message(Long id, User user, String message, LocalDateTime localDateTime) {
    this.id = id;
    this.user = user;
    this.message = message;
    this.localDateTime = localDateTime;
  }

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