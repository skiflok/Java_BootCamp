package ex01.edu.school21.sockets.repositories.messageRepositories;

import ex01.edu.school21.sockets.models.Message;
import ex01.edu.school21.sockets.repositories.userRepositories.UsersRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageRepositoryImpl implements MessageRepository {

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  private final UsersRepository usersRepository;

  @Autowired
  public MessageRepositoryImpl(
      DataSource ds,
      UsersRepository usersRepository) {
    this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(ds);
    this.usersRepository = usersRepository;
  }


  @Override
  public Optional<Message> findById(Long id) {
    String sql = "select * from chat.message where id = :id;";
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("id", id);

    return namedParameterJdbcTemplate.query(sql, params, (rs) -> {
      if (rs.next()) {
        return Optional.of(new Message(
            rs.getLong("id"),
            usersRepository.findById(rs.getLong("id")).orElseThrow(IllegalArgumentException::new),
            rs.getString("text"),
            rs.getTimestamp("date_time").toLocalDateTime()
        ));
      }
      return Optional.empty();
    });
  }

  @Override
  public List<Message> findAll() {
    return null;
  }

  @Override
  public void save(Message entity) {
    String sql = "insert into chat.message (author, text, date_time) values (:author, :text, :date_time)";
    MapSqlParameterSource params = new MapSqlParameterSource();
    params
        .addValue("author", entity.getUser().getId())
        .addValue("text", entity.getMessage())
        .addValue("date_time", entity.getLocalDateTime());

    KeyHolder keyHolder = new GeneratedKeyHolder();
    namedParameterJdbcTemplate.update(sql, params, keyHolder);
    entity.setId((Long) Objects.requireNonNull(keyHolder.getKeys()).get("id"));
  }

  @Override
  public void update(Message entity) {

  }

  @Override
  public void delete(Long id) {

  }
}
