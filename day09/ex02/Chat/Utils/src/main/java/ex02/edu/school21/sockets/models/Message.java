package ex02.edu.school21.sockets.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Message implements Serializable {

  private Long id;
  private User user;

  private Room room;
  private String message;
  @JsonIgnore
  private LocalDateTime localDateTime;
  @JsonIgnore
  private transient final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
      "dd/MM/yy HH:mm");
  private MessageType messageType;

  public Message(Long id,
      User user,
      Room room,
      String message,
      LocalDateTime localDateTime) {
    this.id = id;
    this.user = user;
    this.room = room;
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