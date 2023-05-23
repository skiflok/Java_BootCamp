package edu.school21.chat.repositories;

import edu.school21.chat.models.ChatRoom;
import edu.school21.chat.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    @Override
    public List<User> findAll(int page, int size) throws SQLException {


        /*
        with user_page as (
select * from chat.user
	offset 4
	limit 4),
t as (
select
	up.id as user_id,
	up.login as user_login,
	up.password as user_pass,
	cr.id as owner_chat_id,
	cr.name as owner_chat_name,
	ucr.room_id as socializes_chat_id
from user_page up
left join chat.chat_room cr on up.id = cr.owner
left join chat.user_chat_room ucr on up.id = ucr.user_id
order by up.id, cr.id, ucr.room_id)
select t.*, cr.name as socializes_chat_name from t
left join chat.chat_room cr on t.socializes_chat_id = cr.id
        * */

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

        Optional<List<User>> optionalUserList = Optional.empty();
        try {
            optionalUserList = JdbcTemplate.preparedStatement(sql, (stmt) -> {
                stmt.setInt(1, page);
                stmt.setInt(2, size);
                ResultSet results = stmt.executeQuery();

                ArrayList<User> users = new ArrayList<>();
                List<ChatRoom> createdRoom = new ArrayList<>();
                List<ChatRoom> userSocialized = new ArrayList<>();
                User tempUser = null;
                ChatRoom tempOwnerChat = null;
                ChatRoom tempSocializesChat = null;
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

                    long owner_chat_id = results.getLong("owner_chat_id");
//                    if (owner_chat_id  != 0 &&
//                    users.get(users.size()).getCreatedRoom().stream().noneMatch(chatRoom -> chatRoom.getId() != owner_chat_id)) {
//                        ChatRoom ownerChat = new ChatRoom(
//                                results.getLong("owner_chat_id"),
//                                results.getString("owner_chat_name"),
//                                null,
//                                null);
//                    }


                    boolean contains = users.get(users.size() -1).getCreatedRoom().stream().noneMatch(chatRoom -> chatRoom.getId() == owner_chat_id);
                    System.out.printf("user id = %d owner_chat_id = %d  is contains %b\n", user.getId(), owner_chat_id, contains);


                }

                return Optional.of(users);
            });
        } catch (Exception ignored) {

        }

        return optionalUserList.orElse(null);
    }

}
