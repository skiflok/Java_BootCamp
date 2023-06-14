package school21.spring.service.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class UsersRepositoryJdbcImpl implements UsersRepository {

  private final DataSource ds;

  @Autowired
  public UsersRepositoryJdbcImpl(@Qualifier("hikariDataSource") DataSource ds) {
    this.ds = ds;
  }

  @Override
  public Optional<User> findById(Long id) {
    String sql = "select * from chat.users where id = ?";

    try (PreparedStatement p = ds.getConnection().prepareStatement(sql)) {
      p.setLong(1, id);
      ResultSet resultSet = p.executeQuery();
      if (!resultSet.next()) {
        return Optional.empty();
      }
      return Optional.of(new User(resultSet.getLong(1),
          resultSet.getString(2)));
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }

  @Override
  public List<User> findAll() {
    List<User> userList = new ArrayList<>();

    String sql = "select * from chat.users";

    try (PreparedStatement p = ds.getConnection().prepareStatement(sql)) {
      ResultSet resultSet = p.executeQuery();
      while (resultSet.next()) {
        userList.add(new User(
            resultSet.getLong("id"),
            resultSet.getString("email")
        ));
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return userList;
  }

  @Override
  public void save(User entity) {
    String sql = "insert into chat.users (email) values (?)";

    try (PreparedStatement p = ds.getConnection()
        .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      p.setString(1, entity.getEmail());

      if (p.executeUpdate() == 0) {
        throw new SQLException("User not save");
      }

      ResultSet resultSet = p.getGeneratedKeys();
      if (resultSet.next()) {
        entity.setId(resultSet.getLong("id"));
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public void update(User entity) {
    String sql = "update chat.users set email = ? where id = ?";

    try (PreparedStatement p = ds.getConnection().prepareStatement(sql)) {

      p.setString(1, entity.getEmail());
      p.setLong(2, entity.getId());
      if (p.executeUpdate() == 0) {
        throw new SQLException("User not update");
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void delete(Long id) {
    String sql = "delete from chat.users where id = ?";

    try (PreparedStatement p = ds.getConnection().prepareStatement(sql)) {
      p.setLong(1, id);

      if (p.executeUpdate() == 0) {
        throw new SQLException("User not delete");
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Optional<User> findByEmail(String email) {
    String sql = "select * from chat.users where email = ?";
    try (PreparedStatement p = ds.getConnection().prepareStatement(sql)) {
      p.setString(1, email);
      ResultSet resultSet = p.executeQuery();
      if (resultSet.next()) {
        return Optional.of(new User(
            resultSet.getLong("id"),
            resultSet.getNString("email")
        ));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return Optional.empty();
  }
}
