package edu.school21.ex01.repositories;

import edu.school21.ex01.models.User;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository{

  @Override
  public Optional<User> findById(Long id) {
    return Optional.empty();
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
