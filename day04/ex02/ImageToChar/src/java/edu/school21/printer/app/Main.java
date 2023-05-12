package edu.school21.printer.app;


import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import edu.school21.printer.logic.CommandLineArguments;
import edu.school21.printer.logic.ConsoleImagePrinter;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        try {

            CommandLineArguments cla = new CommandLineArguments();
            JCommander.newBuilder().addObject(cla).build().parse(args);

            String imagePath = "day04/ex02/ImageToChar/src/resources/image.bmp";

            ConsoleImagePrinter consoleImagePrinter =
                    new ConsoleImagePrinter(
                            imagePath,
                            cla.getWhiteColor(),
                            cla.getBlackColor());
            consoleImagePrinter.printImage();

        } catch (IllegalArgumentException | ParameterException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
