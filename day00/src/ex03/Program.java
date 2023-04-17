package ex03;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int weekCount = 0;
        String week;
        long data = 0;

        while (!(week = scanner.nextLine()).equals("42") && ++weekCount <= 18) {
            if (!week.equals("Week " + weekCount)) {
                exitApp(scanner);
            }
            data = data * 10 + getMinScore(scanner);

            scanner.nextLine();
        }

        long reverseData = reserveData(data, weekCount);

        printResult(reverseData, weekCount);

    }

    public static byte getMinScore (Scanner scanner) {
        byte num = 0;
        byte minScore = 10;
        for (int i = 0; i < 5; i++) {

            num = scanner.nextByte();
            if (isNumValid(num)) {
                exitApp(scanner);
            }
            if (num < minScore) {
                minScore = num;
            }
        }

        return minScore;
    }

    public static boolean isNumValid(byte num){
        return (num < 1 || num > 9);
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
