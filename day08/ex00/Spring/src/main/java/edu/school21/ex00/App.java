package edu.school21.ex00;

import edu.school21.ex00.Printer.Printer;
import edu.school21.ex00.Printer.PrinterWithDateTimeImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 */
public class App {

  public static void main(String[] args) {

    ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
    new PrinterWithDateTimeImpl().print("test");
    Printer printer = ctx.getBean("printerWithDateTimeImpl", PrinterWithDateTimeImpl.class);
    printer.print("test");
  }
}
