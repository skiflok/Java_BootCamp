package edu.school21.chat.app;

import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Optional;

public class Program {
    public static void main(String[] args) {
        System.out.println("Hello Chat!");

        MessagesRepository msgRep = new MessagesRepositoryJdbcImpl();

        try (BufferedReader bis = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Enter a message ID");
            Long inputId = Long.parseLong(bis.readLine());
            System.out.println("inputId=" + inputId);

            Optional<Message> message = msgRep.findById(inputId);

            if (message.isPresent()) {
                System.out.println(message.get());
            } else {
                System.out.println("Message not found");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
