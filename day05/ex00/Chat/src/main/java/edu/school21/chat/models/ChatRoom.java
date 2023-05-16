package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class ChatRoom {
    private final int ID;
    private final String name;
    private final String owner;
    private final List<Message> messages;

    public ChatRoom(int id,
                    String name,
                    String owner,
                    List<Message> messages) {
        ID = id;
        this.name = name;
        this.owner = owner;
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatRoom chatRoom = (ChatRoom) o;
        return ID == chatRoom.ID && name.equals(chatRoom.name) && owner.equals(chatRoom.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, name, owner);
    }

    @Override
    public String toString() {
        return "ChatRoom{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", messages=" + messages +
                '}';
    }
}
