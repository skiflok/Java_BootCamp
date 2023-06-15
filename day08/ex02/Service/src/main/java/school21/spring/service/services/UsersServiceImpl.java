package school21.spring.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school21.spring.service.repositories.UsersRepository;

@Component
public class UsersServiceImpl implements UsersService{

  private final UsersRepository usersRepositoryI;

  @Autowired
  public UsersServiceImpl(@Qualifier("usersRepositoryJdbcTemplateImpl") UsersRepository usersRepositoryI) {
    this.usersRepositoryI = usersRepositoryI;
  }

  @Override
  public String signUp(String email) {

    // TODO: 15.06.2023 проверить есть ли такой пользователь вернуть ошибку что ползователь существует

    // TODO: 15.06.2023 сгенерировать пароль
    // TODO: 15.06.2023 сохранить пользователя в БД
    // TODO: 15.06.2023 записать в юзера ID и пароль
    // TODO: 15.06.2023 Вернуть пароль
    return null;
  }



}
