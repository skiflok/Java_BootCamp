package school21.spring.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.utils.passwordGenerator.PasswordGeneratorUtil;

@Component
public class UsersServiceImpl implements UsersService {

  private final UsersRepository usersRepository;
  private final PasswordGeneratorUtil passwordGeneratorUtil;

  @Autowired
  public UsersServiceImpl(
      @Qualifier("usersRepositoryJdbcTemplateImpl") UsersRepository usersRepository,
      PasswordGeneratorUtil passwordGeneratorUtil) {
    this.usersRepository = usersRepository;
    this.passwordGeneratorUtil = passwordGeneratorUtil;
  }

  @Override
  public String signUp(String email) {

    if (email.isEmpty()) {
      throw new IllegalArgumentException("Email isEmpty");
    }

    if (usersRepository.findByEmail(email).isPresent()) {
      throw new IllegalArgumentException("Email " + email +  " is already register");
    }

    String password = passwordGeneratorUtil.generatePassayPassword(10);
    usersRepository.save(new User(null, email, password));
    return password;
  }

}
