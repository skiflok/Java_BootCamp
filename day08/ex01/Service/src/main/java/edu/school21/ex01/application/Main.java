package edu.school21.ex01.application;

import edu.school21.ex01.models.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

  public static void main(String[] args) {
    System.out.println("app");

    ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
    Test test = ctx.getBean("test", Test.class);

    System.out.println(test);

  }

}
