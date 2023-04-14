package ex03;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int weekCount = 0;
        String week;
        long data = 0;
        byte num;
        byte minScore;


        while (!(week = scanner.nextLine()).equals("42") && ++weekCount <= 18) {
//            System.out.println(week);
//            System.out.println(weekCount);
            if (!week.equals("w " + weekCount)) {
                exitApp(scanner);
            }
            minScore = scanner.nextByte();
            for (int i = 0; i < 4; i++) {
                num = scanner.nextByte();
                if (num < minScore) {
                    minScore = num;
                }
            }
            data = data * 10 + minScore;

            System.out.println(minScore);
            scanner.nextLine();
        }
        System.out.println(data);

        long reverseData = 0;
        for (int i = 0; i < weekCount; i++) {
            reverseData *= 10;
            reverseData += data % 10;
            data /= 10;
        }

        for (int i = 1; i <= weekCount; i++) {
            System.out.print("week " + i);
            for (int j = 0; j < reverseData % 10; j++) {
                System.out.print("=");
            }
            System.out.println(">");
            reverseData /= 10;
        }

    }

    public static void exitApp (Scanner scanner) {
        scanner.close();
        System.err.print("Illegal Argument");
        System.exit(-1);
    }

}
