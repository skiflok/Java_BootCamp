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

        int arrSize = 0;
        for (char value : dataArray) {
            c = value;
            if (frequencies[c] == 0) {
                arrSize++;
            }
            frequencies[c]++;
        }

        int[][] res = new int[arrSize][2];
        for (int i = 0, j = 0; i < frequencies.length; i++) {
            if (frequencies[i] != 0) {
                res[j][0] = i;
                res[j][1] = frequencies[i];
                j++;
            }
        }

        sort(res);
        if (res[0][0] > 999) {
            exitApp(scanner);
        }
        printRes(res);

    }

    public static void sort (int [][] res) {
        for (int i = 0; i < res.length - 1; i++) {
            for (int j = 0; j < res.length - i - 1; j++) {
                if (res[j][1] < res[j + 1][1] || (res[j][1] == res[j + 1][1] && res[j][0] > res[j + 1][0])) {
                    int[] temp = res[j];
                    res[j] = res[j + 1];
                    res[j + 1] = temp;
                }
            }
        }
    }
    public static void printRes(int[][] res) {
        double max = res[0][1];
        
        double range = max/10;
        for (double y = max + range; y > 0; y-= range) {
            for (int x = 0; x < res.length && x < 10; x++) {
                if (res[x][1] >= y - range && res[x][1] < y) {
                    System.out.printf("%3d ", res[x][1]);
                } else if (res[x][1] >= y - range) {
                    System.out.printf("%3s ", "#");
                }
            }
            if (y != range) {
                System.out.println();
            }
        }
        for (int x = 0; x < res.length && x < 10; x++) {
            System.out.printf("%3c ", res[x][0]);
        }
    }
    public static void exitApp(Scanner scanner) {
        scanner.close();
        System.err.print("Illegal Argument");
        System.exit(-1);
    }

}
