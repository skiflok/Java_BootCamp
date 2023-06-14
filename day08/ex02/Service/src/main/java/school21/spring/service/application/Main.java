package school21.spring.service.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.config.ApplicationConfig;
import school21.spring.service.models.User;
import school21.spring.service.repositories.DataBaseInitializer;
import school21.spring.service.repositories.UsersRepositoryJdbcImpl;
import school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;
import org.springframework.beans.BeansException;

import javax.sql.DataSource;

public class Main {

  public static void main(String[] args) {
    System.out.println("app");

    ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
    DataSource hikariDataSource = ctx.getBean("hikariDataSource", DataSource.class);

    DataBaseInitializer dbInit = new DataBaseInitializer(hikariDataSource);
    dbInit.init();

    try {

      System.out.println("####### UsersRepositoryJdbcImpl #######");
      UsersRepositoryJdbcImpl usersRepositoryJdbc = ctx.getBean("usersRepositoryJdbcImpl",
          UsersRepositoryJdbcImpl.class);

      System.out.println(usersRepositoryJdbc.findById(1L));
      System.out.println("UsersRepositoryJdbcImpl list users");
      usersRepositoryJdbc.findAll().forEach(System.out::println);

      User testUser = new User(0, "new email");
      usersRepositoryJdbc.save(testUser);
      System.out.println(usersRepositoryJdbc.findById(11L).orElseThrow(RuntimeException::new));

      testUser.setEmail("update email");
      usersRepositoryJdbc.update(testUser);

      System.out.println(usersRepositoryJdbc.findById(11L).orElseThrow(RuntimeException::new));

      usersRepositoryJdbc.delete(11L);
      System.out.println(usersRepositoryJdbc.findById(11L).orElse(null));

      System.out.println("usersRepositoryJdbc.findByEmail " + usersRepositoryJdbc.findByEmail("user5@example.com").orElse(null));

    } catch (RuntimeException e) {
      e.printStackTrace();
    }

    try {
      System.out.println("####### UsersRepositoryJdbcTemplateImpl #######");
      UsersRepositoryJdbcTemplateImpl usersRepositoryJdbcTemplate = ctx.getBean(
          "usersRepositoryJdbcTemplateImpl", UsersRepositoryJdbcTemplateImpl.class);

      System.out.println(usersRepositoryJdbcTemplate.findById(1L));

      usersRepositoryJdbcTemplate.findAll().forEach(System.out::println);

      User user2 = new User(0L, "####@mail.ru");
      usersRepositoryJdbcTemplate.save(user2);
      System.out.println("SAVE " + usersRepositoryJdbcTemplate.findById(user2.getId()).orElse(null));

      user2.setEmail("piy piy");
      usersRepositoryJdbcTemplate.update(user2);
      System.out.println("UPDATE " + usersRepositoryJdbcTemplate.findById(user2.getId()).orElse(null));

      usersRepositoryJdbcTemplate.delete(user2.getId());
      System.out.println("DELETE " + usersRepositoryJdbcTemplate.findById(user2.getId()).orElse(null));

      System.out.println("find by email " + usersRepositoryJdbcTemplate.findByEmail("user5@example.com"));

    } catch (BeansException e) {
      e.printStackTrace();
    }



  }

  }
