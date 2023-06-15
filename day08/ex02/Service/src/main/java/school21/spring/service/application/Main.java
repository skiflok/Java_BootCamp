package school21.spring.service.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.config.ApplicationConfig;
import school21.spring.service.models.User;
import school21.spring.service.repositories.initializerDB.DataBaseInitializer;
import school21.spring.service.repositories.UsersRepositoryJdbcImpl;
import school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;
import org.springframework.beans.BeansException;
import school21.spring.service.services.UsersServiceImpl;

import javax.sql.DataSource;

public class Main {

    public static void main(String[] args) {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        DataBaseInitializer dbInit = ctx.getBean("dataBaseInitializer", DataBaseInitializer.class);
        dbInit.init();

        try {

            System.out.println("####### UsersRepositoryJdbcImpl #######");
            UsersRepositoryJdbcImpl usersRepositoryJdbc = ctx.getBean("usersRepositoryJdbcImpl",
                    UsersRepositoryJdbcImpl.class);

            System.out.println(usersRepositoryJdbc.findById(1L));
            System.out.println("UsersRepositoryJdbcImpl list users");
            usersRepositoryJdbc.findAll().forEach(System.out::println);

            User testUser = new User(0, "new email", null);
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

            User user2 = new User(0L, "####@mail.ru", null);
            usersRepositoryJdbcTemplate.save(user2);
            System.out.println("SAVE " + usersRepositoryJdbcTemplate.findById(user2.getId()).orElse(null));

            user2.setEmail("piy piy");
            usersRepositoryJdbcTemplate.update(user2);
            System.out.println("UPDATE " + usersRepositoryJdbcTemplate.findById(user2.getId()).orElse(null));

            usersRepositoryJdbcTemplate.delete(user2.getId());
            System.out.println("DELETE " + usersRepositoryJdbcTemplate.findById(user2.getId()).orElse(null));

            System.out.println("find by email " + usersRepositoryJdbcTemplate.findByEmail("user5@example.com"));

            System.out.println("find all");
            usersRepositoryJdbcTemplate.findAll().forEach(System.out::println);

        } catch (BeansException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("####### UserService #######");
            UsersServiceImpl usersService = ctx.getBean(
                    "usersServiceImpl", UsersServiceImpl.class);

//        usersService.signUp("user1@gmail.com");

        } catch (BeansException e) {
            e.printStackTrace();
        }


    }

}
