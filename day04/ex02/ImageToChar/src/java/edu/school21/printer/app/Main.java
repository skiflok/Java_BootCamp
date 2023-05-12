package edu.school21.printer.app;


import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import edu.school21.printer.logic.CommandLineArguments;
import edu.school21.printer.logic.ConsoleImagePrinter;

import com.beust.jcommander.Parameter;

import java.io.IOException;

public class Main {


    public static void main(String[] args) {

        try {

            CommandLineArguments cla = new CommandLineArguments();
            JCommander.newBuilder().addObject(cla).build().parse(args);

            System.out.println(cla.getWhiteColor());
            System.out.println(cla.getBlackColor());

            String imagePath = "day04/ex02/ImageToChar/src/resources/image.bmp";
            String white = ".";
            String black = "0";
            ConsoleImagePrinter consoleImagePrinter = new ConsoleImagePrinter(imagePath, white, black);
            consoleImagePrinter.printImage();

        } catch (IllegalArgumentException | ParameterException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
