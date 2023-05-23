package edu.school21.chat.repositories;

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
-- 	*
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
        String sql = "Select * from chat.user";
        Optional<List<User>> optionalUserList = Optional.empty();
        try {
            optionalUserList = JdbcTemplate.preparedStatement(sql, (stmt) -> {
                ResultSet results = stmt.executeQuery();

                ArrayList<User> users = new ArrayList<>();
                while (results.next()) {
                    users.add(new User(
                            results.getLong(1),
                            results.getString(2),
                            results.getString(3),
                            null,
                            null));
                }

                return Optional.of(users);
            });
        } catch (Exception ignored) {

        }

        return optionalUserList.orElse(null);
    }

}
