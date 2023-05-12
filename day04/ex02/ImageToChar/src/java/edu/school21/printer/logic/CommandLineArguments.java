package edu.school21.printer.logic;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class CommandLineArguments {

    @Parameter(names = {"--white"}, description = "Color white", required = true)
    private String whiteColor;

    @Parameter(names = {"--black"}, description = "Color black", required = true)
    private String blackColor;

    public String getWhiteColor() {
        return whiteColor;
    }

    public String getBlackColor() {
        return blackColor;
    }
}

