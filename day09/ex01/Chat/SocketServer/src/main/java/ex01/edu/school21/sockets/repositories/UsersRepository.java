package ex01.edu.school21.sockets.repositories;


import ex01.edu.school21.sockets.models.User;
import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {
  Optional<User> findByName(String name);


}
