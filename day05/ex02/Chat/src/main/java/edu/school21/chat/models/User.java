package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class User {
    private final long id;
    private final String login;
    private final String password;
    private final List<ChatRoom> createdRoom;
    private final List<ChatRoom> userSocialized;

    public User(long id,
                String login,
                String password,
                List<ChatRoom> createdRoom,
                List<ChatRoom> userSocialized) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.createdRoom = createdRoom;
        this.userSocialized = userSocialized;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && login.equals(user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login);
    }

    @Override
    public String toString() {
        return String.format(
                "{id=%d,login=\"%s\",password=\"%s\",createdRoom=%s,userSocialized=%s}",
                id,
                login,
                password,
                createdRoom,
                userSocialized);
    }
}
