package edu.school21.chat.repositories;

import edu.school21.chat.models.ChatRoom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {

    @Override
    public Optional<Message> findById(Long id) {
        String sql = "select * from chat.message where id = ?";
        try {
            return JdbcTemplate.preparedStatement(sql, (stmt) -> {
                stmt.setLong(1, id);
                ResultSet results = stmt.executeQuery();
                if (!results.next()) {
                    return Optional.empty();
                }
                return Optional.of(new Message(
                        id,
                        findUserById(results.getLong(2)).orElse(null),
                        findChatRoomById(results.getLong(3)).orElse(null),
                        results.getString(4),
                        results.getTimestamp(5).toLocalDateTime()));
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void update(Message message) throws SQLException {
        checkMessageParamIfIsBadThrowException(message);

        if (message.getText() == null) {
            message.setText("");
        }

        if (message.getDateTime() == null) {
            message.setDateTime(LocalDateTime.now());
        }

        String sql = "update chat.message set author = ?, room = ?, text = ?, date_time = ? where id = ?";
        JdbcTemplate.preparedStatement(sql, (stmt) -> {
            stmt.setLong(1, message.getAuthor().getId());
            stmt.setLong(2, message.getRoom().getId());
            stmt.setString(3, message.getText());
            stmt.setTimestamp(4, Timestamp.valueOf(message.getDateTime()));
            stmt.setLong(5, message.getId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new NotSavedSubEntityException("changes not save");
            }

        });


    }

    @Override
    public void save(Message message) throws SQLException {
        checkMessageParamIfIsBadThrowException(message);
        saveMessage(message);
    }

    private void checkMessageParamIfIsBadThrowException(Message message) {

        if (message.getAuthor() == null) {
            throw new NotSavedSubEntityException("Author is null");
        }
        if (message.getRoom() == null) {
            throw new NotSavedSubEntityException("ChatRoom is null");
        }

        if (!findUserById(message.getAuthor().getId()).isPresent()) {
            throw new NotSavedSubEntityException("User not found");
        }
        if (!findChatRoomById(message.getRoom().getId()).isPresent()) {
            throw new NotSavedSubEntityException("ChatRoom not found");
        }

    }

    private void saveMessage(Message message) throws SQLException {
        String sql = "insert into chat.message (author, room, text, date_time) values   (?, ?, ?, ?)";

        JdbcTemplate.preparedStatement(sql, (stmt) -> {
            stmt.setLong(1, message.getAuthor().getId());
            stmt.setLong(2, message.getRoom().getId());
            stmt.setString(3, message.getText());
            stmt.setTimestamp(4, Timestamp.valueOf(message.getDateTime()));
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    long generatedId = generatedKeys.getLong(1); // Получение значения сгенерированного id
                    message.setId(generatedId);
                }
            } else {
                throw new NotSavedSubEntityException("id not received");
            }
        });
    }

    private Optional<User> findUserById(Long id) {
        String sql = "select * from chat.user where id = ?";
        try {
            return JdbcTemplate.preparedStatement(sql, (stmt) -> {
                stmt.setLong(1, id);
                ResultSet results = stmt.executeQuery();
                if (!results.next()) {
                    return Optional.empty();
                }
                return Optional.of(new User(
                        results.getLong(1),
                        results.getString(2),
                        results.getString(3),
                        null,
                        null
                ));
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Optional<ChatRoom> findChatRoomById(Long id) {

        String sql = "select * from chat.chat_room where id = ?";

        try {
            return JdbcTemplate.preparedStatement(sql, (stmt) -> {
                stmt.setLong(1, id);
                ResultSet results = stmt.executeQuery();
                if (!results.next()) {
                    return Optional.empty();
                }
                return Optional.of(new ChatRoom(
                        results.getLong(1),
                        results.getString(2),
                        null,
                        null
                ));
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
