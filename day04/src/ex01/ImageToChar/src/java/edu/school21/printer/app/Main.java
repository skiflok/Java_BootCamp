package edu.school21.printer.app;

import edu.school21.printer.logic.CommandLineArguments;
import edu.school21.printer.logic.ConsoleImagePrinter;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        try {
            CommandLineArguments.checkInputParam(args);
            String white = args[0];
            String black = args[1];
            String imagePath = args[2];
            ConsoleImagePrinter consoleImagePrinter = new ConsoleImagePrinter(imagePath, white, black);
            consoleImagePrinter.printImage();

        } catch (IllegalArgumentException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
