package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ConsoleImagePrinter {

    private String imagePath;
    private String whiteColorSymbol;
    private String blackColorSymbol;
    private final int blackColor;


    public ConsoleImagePrinter(String imagePath, String whiteColorSymbol, String blackColorSymbol) {
        this.imagePath = imagePath;
        this.whiteColorSymbol = whiteColorSymbol;
        this.blackColorSymbol = blackColorSymbol;
        this.blackColor = 0xff000000;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getWhiteColorSymbol() {
        return whiteColorSymbol;
    }

    public void setWhiteColorSymbol(String whiteColorSymbol) {
        this.whiteColorSymbol = whiteColorSymbol;
    }

    public String getBlackColorSymbol() {
        return blackColorSymbol;
    }

    public void setBlackColorSymbol(String blackColorSymbol) {
        this.blackColorSymbol = blackColorSymbol;
    }

    public void printImage() throws IOException {

        BufferedImage myPicture = ImageIO.read(new File(imagePath));

        for (int i = 0; i < myPicture.getHeight(); i++) {
            for (int j = 0; j < myPicture.getWidth(); j++) {
                if (myPicture.getRGB(j, i) == blackColor) {
                    System.out.print(blackColorSymbol);
                } else System.out.print(whiteColorSymbol);
            }
            System.out.println();
        }
    }
}
