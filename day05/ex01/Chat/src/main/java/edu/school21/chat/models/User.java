package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class User {
    private final long ID;
    private final String login;
    private final String password;
    private final List<ChatRoom> createdRoom;
    private final List<ChatRoom> userSocialized;

    public User(long id,
                String login,
                String password,
                List<ChatRoom> createdRoom,
                List<ChatRoom> userSocialized) {
        ID = id;
        this.login = login;
        this.password = password;
        this.createdRoom = createdRoom;
        this.userSocialized = userSocialized;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return ID == user.ID && login.equals(user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, login);
    }

    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", createdRoom=" + createdRoom +
                ", userSocialized=" + userSocialized +
                '}';
    }
}
