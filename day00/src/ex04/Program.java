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






        for (int i = 0; i < charCount; i++) {
            System.out.printf("%c: %d\n", res[i][0], res[i][1]);
        }


        int height = 10;
        int max = res[0][1];
//        for (int i = 0; i < charCount; i++) {
//            for (int j = 0; j < height; j++) {
//                if (res[charCount][1] == max) {
//                    System.out.printf("%d", res[i][1]);
//                } else {
//                    System.out.printf("#");
//                }
//                System.out.println();
//            }
//        }

        for (int i = 1; i <= height; i++) {
            for (int j = 0; j < charCount; j++) {
                if (res[j][1] == max / i) {
                    System.out.printf("%d", res[i][1]);
                } else  {
                    System.out.print("#");
                }
            }
            System.out.println();
        }

        System.out.println();
        for (int i = 0; i < charCount; i++) {
            System.out.printf("%c", res[i][0]);
        }

    }

    public static void exitApp(Scanner scanner) {
        scanner.close();
        System.err.print("Illegal Argument");
        System.exit(-1);
    }

}
