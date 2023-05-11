package edu.school21.printer.app;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        if (args.length != 3) {
            throw new IllegalArgumentException("Неверное количество аргументов");
        }

        String white = args[0];
        String black = args[1];
        String imagePath = args[2];

        if (white.length() != 1 || black.length() != 1) {
            throw new IllegalArgumentException("Неверная длина аргумента для отбражения цвета");
        }

//        if (!Files.exists(Paths.get(imagePath)) || !Files.isRegularFile(Paths.get(imagePath))) {
//
//        }
        BufferedImage myPicture = ImageIO.read(new File(imagePath));

        int blackColor = 0xff000000;

        for (int i = 0; i < myPicture.getHeight(); i++) {
            for (int j = 0; j < myPicture.getWidth(); j++) {
                if (myPicture.getRGB(j, i) == blackColor) {
                    System.out.print(black);
                } else System.out.print(white);
            }
            System.out.println();
        }
    }

}
