package edu.school21.sockets.app;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.repositories.UsersRepository;
import edu.school21.sockets.repositories.UsersRepositoryJdbcTemplateImpl;
import edu.school21.sockets.repositories.utils.DataBaseInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        ApplicationContext ctx = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
        ctx.getBean("dataBaseInitializer", DataBaseInitializer.class).init();
        UsersRepository usersRepository = ctx.getBean("usersRepositoryJdbcTemplateImpl", UsersRepositoryJdbcTemplateImpl.class);

        usersRepository.findAll().forEach(System.out::println);

    }
}
