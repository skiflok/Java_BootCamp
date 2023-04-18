package ex04;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        String testString = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAASSSSSSSSSSSSSSS" +
                "SSSSSSSSSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDWEWWKFK" +
                "KDKKDSKAKLSLDKSKALLLLLLLLLLRTRTETWTWWWWWWW" +
                "WWWOOOOOOO42";
        InputStream is = new ByteArrayInputStream(testString.getBytes());
        System.setIn(is);

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

        int charSize = 0;

        for (char value : dataArray) {
            c = value;
            if (frequencies[c] == 0) {
                charSize++;
            }
            frequencies[c]++;
        }

        int[][] res = new int[charSize][2];
        int charCount = 0;
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] != 0) {
                res[charCount][0] = i;
                res[charCount][1] = frequencies[i];
                charCount++;
            }
        }

        for (int i = 0; i < charCount - 1; i++) {
            for (int j = 0; j < charCount - i - 1; j++) {
                if (res[j][1] < res[j + 1][1] || (res[j][1] == res[j + 1][1] && res[j][0] > res[j + 1][0])) {
                    int[] temp = res[j];
                    res[j] = res[j + 1];
                    res[j + 1] = temp;
                }
            }
        }


        double max = res[0][1];
        double range = max/10;
        for (double y = max + range; y > 0; y-= range) {
            for (int x = 0; x < charCount && x < 10; x++) {
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
        for (int x = 0; x < charCount && x < 10; x++) {
            System.out.printf("%3c ", res[x][0]);
        }

    }

//    public static void printRes() {
//        double max = res[0][1];
//        double range = max/10;
//        for (double y = max + range; y > 0; y-= range) {
//            for (int x = 0; x < charCount && x < 10; x++) {
//                if (res[x][1] >= y - range && res[x][1] < y) {
//                    System.out.printf("%3d ", res[x][1]);
//                } else if (res[x][1] >= y - range) {
//                    System.out.printf("%3s ", "#");
//                }
//            }
//            if (y != range) {
//                System.out.println();
//            }
//        }
//        for (int x = 0; x < charCount && x < 10; x++) {
//            System.out.printf("%3c ", res[x][0]);
//        }
//    }
    public static void exitApp(Scanner scanner) {
        scanner.close();
        System.err.print("Illegal Argument");
        System.exit(-1);
    }

}
