package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class ChatRoom {
    private final long id;
    private final String name;
    private final User owner;
    private final List<Message> messages;

    public ChatRoom(long id,
                    String name,
                    User owner,
                    List<Message> messages) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatRoom chatRoom = (ChatRoom) o;
        return id == chatRoom.id && name.equals(chatRoom.name) && owner.equals(chatRoom.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, owner);
    }

    @Override
    public String toString() {
        return String.format(
                "{id=%d,name=\"%s\",creator=%s,messages=%s}",
                id,
                name,
                owner,
                messages);
    }
}
