package ex01.edu.school21.sockets.repositories.userRepositories;


import ex01.edu.school21.sockets.models.User;
import ex01.edu.school21.sockets.repositories.CrudRepository;
import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {
  Optional<User> findByName(String name);
}
