package ex02.edu.school21.sockets.repositories.userRepositories;


import ex02.edu.school21.sockets.models.User;
import ex02.edu.school21.sockets.repositories.CrudRepository;
import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {
  Optional<User> findByName(String name);
}
