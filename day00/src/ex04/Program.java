package ex04;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        if (!scanner.hasNext()) {
            exitApp(scanner);
        }

        char c;
        String data = scanner.nextLine();
        if (data.length() == 0) {
            exitApp(scanner);
        }

        char[] dataArray = data.toCharArray();
        int[] frequencies = new int[65536];

        for (char value : dataArray) {
            c = value;
            frequencies[c]++;
        }

        int [][] res = new int[65536][2];
        int charCount = 0;
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] != 0) {
                res[charCount][0] = i;
                res[charCount][1] = frequencies [i];
                charCount++;
            }
        }

        for (int i = 0; i < charCount - 1; i++) {
            for (int j = 0; j < charCount - i - 1; j++) {
                if (res[j][1] < res[j+1][1] || (res[j][1] == res[j+1][1] && res[j][0] > res[j+1][0])) {
                    int[] temp = res[j];
                    res[j] = res[j+1];
                    res[j+1] = temp;
                }
            }
        }

        int outCountString = 10;

        for (int i = 0; i < charCount; i++) {
            System.out.printf("%c: %d\n", res[i][0], res[i][1]);
        }


//        for (int i = 0; i < frequencies.length; i++) {
//            if (frequencies[i] != 0) {
//                System.out.printf("%c: %d\n", i, frequencies[i]);
//            }
//        }


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
