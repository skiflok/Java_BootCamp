package edu.school21.chat.repositories;

import edu.school21.chat.models.ChatRoom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import java.sql.*;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {

    private final JdbcTemplate jdbcTemplate;

    public MessagesRepositoryJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Message> findById(Long id) {
        String sql = "select * from chat.message where id = ?";
        try {
            return jdbcTemplate.preparedStatement(sql, (stmt) -> {
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

    public Optional<User> findUserById(Long id) {
        String sql = "select * from chat.user where id = ?";
        try {
            return jdbcTemplate.preparedStatement(sql, (stmt) -> {
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

    public Optional<ChatRoom> findChatRoomById(Long id) {

        String sql = "select * from chat.chat_room where id = ?";

        try {
            return jdbcTemplate.preparedStatement(sql, (stmt) -> {
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
