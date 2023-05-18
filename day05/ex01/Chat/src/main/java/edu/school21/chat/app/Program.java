package edu.school21.chat.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

/**
 * Hello world!
 */
public class Program {
    public static void main(String[] args) {
        System.out.println("Hello Chat!");

        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/chatDataBase",
                "postgres",
                "admin");
             BufferedReader bis = new BufferedReader(new InputStreamReader(System.in))
        ) {

            System.out.println("Enter a message ID");
            int inputId = Integer.parseInt(bis.readLine());
            System.out.println("inputId=" + inputId);
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("select * from chat.message where id =" + inputId);
            while (results.next()) {

                int id = results.getInt(1);
                int author = results.getInt(2);
                int room = results.getInt(3);
                String text = results.getString(4);
                String date = results.getString(5);

                System.out.printf("id = %d author = %d room = %d text = %s date = %s",
                        id,
                        author,
                        room,
                        text,
                        date);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
