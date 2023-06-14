package school21.spring.service.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;

import java.util.Objects;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@Component
public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Autowired
  public UsersRepositoryJdbcTemplateImpl( @Qualifier("driverManagerDataSource") DataSource ds) {
    namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(ds);
  }

  @Override
  public Optional<User> findById(Long id) {
    String sql = "select * from chat.users where id = :id;";
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("id", id);

    return namedParameterJdbcTemplate.query(sql, params, (rs) -> {
      if (rs.next()) {
        return Optional.of(new User(rs.getLong("id"), rs.getString("email")));
      }
      return Optional.empty();
    });
  }

  @Override
  public List<User> findAll() {
    String sql = "select * from chat.users";
    return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
  }

  @Override
  public void save(User entity) {
    String sql = "insert into chat.users (email) values (:email)";
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("email", entity.getEmail());

    KeyHolder keyHolder = new GeneratedKeyHolder();
    namedParameterJdbcTemplate.update(sql, params, keyHolder);
    entity.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

  }

  @Override
  public void update(User entity) {
    String sql = "update chat.users set email = :email where id = :id";
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("id", entity.getId());
    params.addValue("email", entity.getEmail());
    if (namedParameterJdbcTemplate.update(sql, params) == 0) {
      throw new IllegalArgumentException("Failed to update entity");
    }
  }

  @Override
  public void delete(Long id) {
    String sql = "delete from chat.users where id = :id;";

    if (namedParameterJdbcTemplate.update(sql,
        new MapSqlParameterSource().addValue("id", id)) == 0) {
      throw new IllegalArgumentException("Failed to delete entity");
    }
  }

  @Override
  public Optional<User> findByEmail(String email) {
    String sql = "select * from chat.users where email = :email";
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("email", email);
    List<User> users = namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) ->
        new User(rs.getLong("id"), rs.getString("email"))
    );

    if (users.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(users.get(0));
    }

  }
}
