package ex01.edu.school21.sockets.repositories.messageRepositories;

import ex01.edu.school21.sockets.models.Message;
import ex01.edu.school21.sockets.models.User;
import ex01.edu.school21.sockets.repositories.userRepositories.UsersRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageRepositoryImpl implements MessageRepository {

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  private final UsersRepository usersRepository;

  public MessageRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
      UsersRepository usersRepository) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
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
            usersRepository.findById(rs.getLong("id")).orElseThrow(IllegalArgumentException::new),
            rs.getString("text"),
            rs.getString("password")
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

  }

  @Override
  public void update(Message entity) {

  }

  @Override
  public void delete(Long id) {

  }
}
