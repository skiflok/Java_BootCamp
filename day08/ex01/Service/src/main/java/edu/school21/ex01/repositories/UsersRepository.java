package edu.school21.ex01.repositories;

import edu.school21.ex01.models.User;
import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {
  Optional<User> findByEmail(String email);

}
