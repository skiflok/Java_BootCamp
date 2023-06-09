package edu.school21.ex00;

import edu.school21.ex00.preProcessor.PreProcessor;
import edu.school21.ex00.preProcessor.PreProcessorToLower;
import edu.school21.ex00.preProcessor.PreProcessorToUpperImpl;
import edu.school21.ex00.printer.Printer;
import edu.school21.ex00.printer.PrinterWithDateTimeImpl;
import edu.school21.ex00.printer.PrinterWithPrefixImpl;
import edu.school21.ex00.renderer.Renderer;
import edu.school21.ex00.renderer.RendererErrImpl;
import edu.school21.ex00.renderer.RendererStandardImpl;
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

    System.out.println();

    PreProcessor preProcessorUpper = new PreProcessorToUpperImpl();
    PreProcessor preProcessorLower = new PreProcessorToLower();

    Renderer rendererStandard= new RendererStandardImpl(preProcessorLower);
    Renderer rendererErr= new RendererErrImpl(preProcessorUpper);

    PrinterWithPrefixImpl printerWithPrefixClass = new PrinterWithPrefixImpl(rendererStandard);
    printerWithPrefixClass.setPrefix("Qwerty");
    Printer printerWithTimeClass = new PrinterWithDateTimeImpl(rendererErr);

    printerWithPrefixClass.print("printerWithPrefixClass");
    printerWithTimeClass.print("printerWithTimeClass");



    ctx.close();
  }
}
