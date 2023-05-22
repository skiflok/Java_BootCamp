package edu.school21.chat.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Message {
    private Long id;
    private User author;
    private ChatRoom room;
    private String text;
    private LocalDateTime dateTime;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");


    public Message(Long id,
                   User author,
                   ChatRoom room,
                   String text,
                   LocalDateTime dateTime) {
        this.id = id;
        this.author = author;
        this.room = room;
        this.text = text;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public ChatRoom getRoom() {
        return room;
    }

    public void setRoom(ChatRoom room) {
        this.room = room;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id) && author.equals(message.author) && room.equals(message.room) && text.equals(message.text) && dateTime.equals(message.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, room, text, dateTime);
    }

    @Override
    public String toString() {

        return String.format(
                "Message : {\n" +
                        "id=%d,\n" +
                        "author=%s,\n" +
                        "room=%s,\n" +
                        "text=\"%s\",\n" +
                        "dateTime=%s\n" +
                        "}",
                id,
                author,
                room,
                text,
                dateTime.format(formatter));
    }
}
