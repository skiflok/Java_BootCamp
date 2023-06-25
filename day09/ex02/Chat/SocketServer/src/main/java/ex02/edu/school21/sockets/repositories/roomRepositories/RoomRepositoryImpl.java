package ex02.edu.school21.sockets.repositories.roomRepositories;

import ex02.edu.school21.sockets.models.Room;
import java.util.List;
import java.util.Optional;

public class RoomRepositoryImpl implements RoomRepository{

  @Override
  public Optional<Room> findById(Long id) {
    return Optional.empty();
  }

  @Override
  public List<Room> findAll() {
    return null;
  }

  @Override
  public void save(Room entity) {

  }

  @Override
  public void update(Room entity) {

  }

  @Override
  public void delete(Long id) {

  }
}
