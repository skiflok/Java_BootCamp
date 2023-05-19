package edu.school21.chat.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Message {
    private final long id;
    private final User author;
    private final ChatRoom room;
    private final String text;
    private final LocalDateTime dateTime;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");


    public Message(long id,
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id == message.id && author.equals(message.author) && room.equals(message.room) && text.equals(message.text) && dateTime.equals(message.dateTime);
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
