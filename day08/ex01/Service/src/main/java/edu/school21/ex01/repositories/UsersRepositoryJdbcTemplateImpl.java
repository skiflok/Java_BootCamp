package edu.school21.ex01.repositories;

import edu.school21.ex01.models.User;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.lang.NonNull;


public class UsersRepositoryJdbcTemplateImpl implements UsersRepository{


  private final DataSource ds;
  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public UsersRepositoryJdbcTemplateImpl(DataSource ds) {
    this.ds = ds;
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
