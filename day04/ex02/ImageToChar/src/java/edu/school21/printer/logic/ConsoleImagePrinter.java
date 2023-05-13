package edu.school21.printer.logic;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConsoleImagePrinter {

    private final Path imagePath;
    private final String whiteColorSymbol;
    private final String blackColorSymbol;
    private final int blackColor;

    public ConsoleImagePrinter(Path imagePath, String whiteColorSymbol, String blackColorSymbol) {
        this.imagePath = imagePath;
        this.whiteColorSymbol = whiteColorSymbol;
        this.blackColorSymbol = blackColorSymbol;
        this.blackColor = 0xff000000;
        checkPathFile(imagePath);
    }

    public static void checkPathFile (Path imagePath) {
        if (!Files.exists(imagePath) || !Files.isRegularFile(imagePath)) {
            throw new IllegalArgumentException("Файл не существует");
        }
    }

    public void printImage() throws IOException {

        BufferedImage myPicture = ImageIO.read(imagePath.toFile());

        ColoredPrinter whitePrinter =
                new ColoredPrinter.Builder(1, false)
                        .foreground(Ansi.FColor.valueOf(whiteColorSymbol)).build();

        ColoredPrinter blackPrinter =
                new ColoredPrinter.Builder(1, false)
                        .foreground(Ansi.FColor.valueOf(blackColorSymbol)).build();

        for (int i = 0; i < myPicture.getHeight(); i++) {
            for (int j = 0; j < myPicture.getWidth(); j++) {
                if (myPicture.getRGB(j, i) == blackColor) {
                    blackPrinter.print('\u2588');
                } else {
                    whitePrinter.print('\u2588');
                }
            }
            System.out.println();
        }
    }
}
