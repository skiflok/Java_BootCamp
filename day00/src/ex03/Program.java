package ex03;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int weekCount = 0;
        String week;
        long data = 0;
        byte num;
        byte minScore = 10;

        while (!(week = scanner.nextLine()).equals("42") && ++weekCount <= 18) {
            if (!week.equals("w " + weekCount)) {
                exitApp(scanner);
            }

            for (int i = 0; i < 5; i++) {
                num = scanner.nextByte();
                if (num < 1 || num > 9) {
                    exitApp(scanner);
                }
                if (num < minScore) {
                    minScore = num;
                }

            }
            data = data * 10 + minScore;

            System.out.println(minScore);
            scanner.nextLine();
        }
        System.out.println(data);

        long reverseData = reserveData(data, weekCount);

        printResult(reverseData, weekCount);

    }

    public static byte getMinScore (Scanner scanner) {
        byte num = 0;
        byte minScore = 0;
        for (int i = 0; i < 5; i++) {

            num = scanner.nextByte();
            if (num < 1 || num > 9) {
                exitApp(scanner);
            }
            if (num < minScore) {
                minScore = num;
            }
        }

        return num;
    }

    public static void printResult(long reverseData, int weekCount) {
        for (int i = 1; i <= weekCount; i++) {
            System.out.print("week " + i);
            for (int j = 0; j < reverseData % 10; j++) {
                System.out.print("=");
            }
            System.out.println(">");
            reverseData /= 10;
        }
    }

    public static long reserveData(long data, int weekCount) {
        long reverseData = 0;
        for (int i = 0; i < weekCount; i++) {
            reverseData *= 10;
            reverseData += data % 10;
            data /= 10;
        }
        return reverseData;
    }

    public static void exitApp(Scanner scanner) {
        scanner.close();
        System.err.print("Illegal Argument");
        System.exit(-1);
    }

}
