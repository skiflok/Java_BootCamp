package ex01.edu.school21.sockets.repositories.messageRepositories;

import ex01.edu.school21.sockets.models.Message;
import java.util.List;
import java.util.Optional;

public class MessageRepositoryImpl implements MessageRepository {


  @Override
  public Optional<Message> findById(Long id) {
    return Optional.empty();
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
