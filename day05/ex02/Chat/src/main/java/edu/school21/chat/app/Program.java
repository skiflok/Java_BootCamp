package edu.school21.chat.app;

import edu.school21.chat.models.ChatRoom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;
import edu.school21.chat.repositories.NotSavedSubEntityException;

import java.time.LocalDateTime;
import java.util.ArrayList;


public class Program {
    public static void main(String[] args) {
        System.out.println("Hello Chat!");

        MessagesRepository msgRep = new MessagesRepositoryJdbcImpl();

        try {
            User creator = new User(7L, "user", "user", new ArrayList(), new ArrayList());
            ChatRoom room = new ChatRoom(8L, "room", creator, new ArrayList());
            Message message = new Message(null, creator, room, "Hello!", LocalDateTime.now());
            msgRep.save(message);
            System.out.println(message.getId()); // ex. id == 11

        } catch (NotSavedSubEntityException e) {
            e.printStackTrace();
        }
    }

}
