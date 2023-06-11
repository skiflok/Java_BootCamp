package edu.school21.ex01.application;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.ex01.models.Test;
import edu.school21.ex01.repositories.DataBaseInitializer;
import edu.school21.ex01.repositories.UsersRepositoryJdbcTemplateImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;

public class Main {

  public static void main(String[] args) {
    System.out.println("app");

    ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
    Test test = ctx.getBean("test", Test.class);


    UsersRepositoryJdbcTemplateImpl usersRepositoryJdbcTemplate = ctx.getBean("usersRepositoryJdbcTemplateImpl", UsersRepositoryJdbcTemplateImpl.class);

    DataSource hikariDataSource = ctx.getBean("hikariDataSource", DataSource.class);
    DataBaseInitializer dbInit = new DataBaseInitializer(hikariDataSource);

    dbInit.init();

    System.out.println();

    System.out.println(test);

  }

}
