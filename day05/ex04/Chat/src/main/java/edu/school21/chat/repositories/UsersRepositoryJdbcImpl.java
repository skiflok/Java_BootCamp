package edu.school21.chat.repositories;

import edu.school21.chat.models.ChatRoom;
import edu.school21.chat.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {

  private final JdbcTemplate jdbcTemplate;

  public UsersRepositoryJdbcImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<User> findAll(int page, int size) throws SQLException {

    String sql =
        "with user_page as (\n" +
            "select * from chat.user\n" +
            "offset ? limit ?),\n" +
            "t as (\n" +
            "select\n" +
            "up.id as user_id,\n" +
            "up.login as user_login,\n" +
            "up.password as user_pass,\n" +
            "cr.id as owner_chat_id,\n" +
            "cr.name as owner_chat_name,\n" +
            "ucr.room_id as socializes_chat_id\n" +
            "from user_page up\n" +
            "left join chat.chat_room cr on up.id = cr.owner\n" +
            "left join chat.user_chat_room ucr on up.id = ucr.user_id\n" +
            "order by up.id, cr.id, ucr.room_id)\n" +
            "select t.*, cr.name as socializes_chat_name from t\n" +
            "left join chat.chat_room cr on t.socializes_chat_id = cr.id";

    Optional<List<User>> optionalUserList = jdbcTemplate.preparedStatement(sql, (stmt) -> {
      stmt.setInt(1, page * size - 1);
      stmt.setInt(2, size);
      ResultSet results = stmt.executeQuery();

      ArrayList<User> users = new ArrayList<>();

      while (results.next()) {

        User user = new User(
            results.getLong("user_id"),
            results.getString("user_login"),
            results.getString("user_pass"),
            new ArrayList<>(),
            new ArrayList<>());
        if (!users.contains(user)) {
          users.add(user);
        }

        user = users.get(users.size() - 1);
        long owner_chat_id = results.getLong("owner_chat_id");
        if (owner_chat_id != 0 &&
            user.getCreatedRoom().stream()
                .noneMatch(chatRoom -> chatRoom.getId() == owner_chat_id)) {
          ChatRoom ownerChat = new ChatRoom(
              results.getLong("owner_chat_id"),
              results.getString("owner_chat_name"),
              null,
              null);
          user.getCreatedRoom().add(ownerChat);
        }

        long socializes_chat_id = results.getLong("socializes_chat_id");
        if (socializes_chat_id != 0 &&
            user.getUserSocialized().stream()
                .noneMatch(chatRoom -> chatRoom.getId() == socializes_chat_id)) {
          ChatRoom socializesChat = new ChatRoom(
              results.getLong("socializes_chat_id"),
              results.getString("socializes_chat_name"),
              null,
              null);
          user.getUserSocialized().add(socializesChat);
        }
      }

      return Optional.of(users);
    });

    return optionalUserList.orElse(null);
  }
}
