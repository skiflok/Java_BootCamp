package edu.school21.ex00;

import edu.school21.ex00.printer.Printer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 */
public class App {

  public static void main(String[] args) {

    ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");

    Printer printerWithPrefix = ctx.getBean("printerWithPrefix", Printer.class);
    printerWithPrefix.print("printerWithPrefix");

    Printer printerWithTime = ctx.getBean("printerWithTime", Printer.class);
    printerWithTime.print("printerWithTime");

    ctx.close();
  }
}
