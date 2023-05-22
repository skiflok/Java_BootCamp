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
