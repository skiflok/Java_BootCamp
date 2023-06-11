package edu.school21.ex01.repositories;

import edu.school21.ex01.models.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UsersRepositoryJdbcImpl implements UsersRepository {

    @NonNull
    private final DataSource ds;

    @Override
    public Optional<User> findById(Long id) {
        String sql = "select * from chat.users where id = ?";

        try (PreparedStatement p = ds.getConnection().prepareStatement(sql)) {
            p.setLong(1, id);
            ResultSet resultSet = p.executeQuery();
            while (resultSet.next()) {
                return Optional.of(new User(resultSet.getLong(1),
                        resultSet.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void save(User entity) {

    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }
}
