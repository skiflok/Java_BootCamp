package edu.school21.printer.app;


import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import edu.school21.printer.logic.CommandLineArguments;
import edu.school21.printer.logic.ConsoleImagePrinter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {

        try {

            CommandLineArguments cla = new CommandLineArguments();
            JCommander.newBuilder().addObject(cla).build().parse(args);

            Path imagePath =
                    Paths.get("target/resources/image.bmp").toAbsolutePath().normalize();

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
