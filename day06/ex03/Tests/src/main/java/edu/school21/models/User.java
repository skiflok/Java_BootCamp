package edu.school21.models;

import java.util.Objects;

public class User {
    private final long Identifier;
    private final String Login;
    private final String Password;
    private final boolean Authentication;

    public User(long identifier, String login, String password, boolean authentication) {
        Identifier = identifier;
        Login = login;
        Password = password;
        Authentication = authentication;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Identifier == user.Identifier && Authentication == user.Authentication && Login.equals(user.Login) && Password.equals(user.Password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Identifier, Login, Password, Authentication);
    }

    @Override
    public String toString() {
        return "User{" +
                "Identifier=" + Identifier +
                ", Login='" + Login + '\'' +
                ", Password='" + Password + '\'' +
                ", Authentication=" + Authentication +
                '}';
    }
}
