package edu.school21.chat.repositories;

import edu.school21.chat.models.Message;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    @Override
    public Optional<Message> findById(Long id) {

        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/chatDataBase",
                "postgres",
                "admin")
        ) {

            Statement statement = connection.createStatement();
            String sql = "select * from chat.message where id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet results = stmt.executeQuery();
//            ResultSet results = statement.executeQuery("select * from chat.message where id =" + inputId);
            if (!results.next()) {
                return Optional.empty();
            }

            int msgId = results.getInt(1);
            int author = results.getInt(2);
            int room = results.getInt(3);
            String text = results.getString(4);
            LocalDateTime date = results.getTimestamp(5).toLocalDateTime();

            System.out.printf("id = %d author = %d room = %d text = %s date = %s\n",
                    msgId,
                    author,
                    room,
                    text,
                    date);

            return Optional.of(new Message(msgId, null, null, text, date));


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
