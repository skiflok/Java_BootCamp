package edu.school21.chat.models;

import java.util.List;

public class User {
    private int ID;
    private String login;
    private String password;
    private List<Chatroom> createdRoom;
    private List<Chatroom> userSocialized;
}
