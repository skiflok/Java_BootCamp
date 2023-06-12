package edu.school21.ex01.application;

import edu.school21.ex01.models.Test;
import edu.school21.ex01.repositories.DataBaseInitializer;
import edu.school21.ex01.repositories.UsersRepositoryJdbcImpl;
import edu.school21.ex01.repositories.UsersRepositoryJdbcTemplateImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;

public class Main {

  public static void main(String[] args) {
    System.out.println("app");

    ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
    Test test = ctx.getBean("test", Test.class);
    System.out.println(test);
    DataSource hikariDataSource = ctx.getBean("hikariDataSource", DataSource.class);
    DataBaseInitializer dbInit = new DataBaseInitializer(hikariDataSource);
    dbInit.init();

    UsersRepositoryJdbcTemplateImpl usersRepositoryJdbcTemplate = ctx.getBean(
        "usersRepositoryJdbcTemplateImpl", UsersRepositoryJdbcTemplateImpl.class);
    UsersRepositoryJdbcImpl usersRepositoryJdbc = ctx.getBean("usersRepositoryJdbcImpl",
        UsersRepositoryJdbcImpl.class);

    System.out.println();
    System.out.println(usersRepositoryJdbc.findById(1L));



  }

}
