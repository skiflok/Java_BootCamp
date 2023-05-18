package edu.school21.chat.app;

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
                "admin"
        )) {

            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("select * from chat.user");
            while (results.next()) {

                int id = results.getInt(1);
                String name = results.getString(2);
                System.out.println(results.getRow() + ". " + id + "\t" + name);
            }

            int rowsCount = statement.executeUpdate("UPDATE chat.user set password='newPass' where id=1 ");
            System.out.println("rowsCount = " +  rowsCount);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
