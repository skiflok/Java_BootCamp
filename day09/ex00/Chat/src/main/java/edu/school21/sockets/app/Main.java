package edu.school21.sockets.app;

import edu.school21.sockets.config.SocketsApplicationConfig;
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

    }
}
