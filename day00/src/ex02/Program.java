package ex02;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int count = 0;
        int num = 0;
        while (num != 42) {
            if (!(scanner.hasNext() && scanner.hasNextInt())) {
                scanner.close();
                System.err.print("Illegal Argument");
                System.exit(-1);
            }
            num = scanner.nextInt();
            if (isSimple(sumOfDigits(num))) {
                count++;
            }
        }
        System.out.println("Count of coffee-request - " + count);
        scanner.close();
    }


    static int sumOfDigits (int num) {
        int sum = 0;
        while (num != 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }

    static boolean isSimple (int num) {
        boolean isSimple = true;
        if (num <= 1) {
            isSimple = false;
            System.err.print("Illegal Argument");
            System.exit(-1);
        } else {
            for (int i = 2; i * i < num; i++) {
                if (num % i == 0) {
                    isSimple = false;
                    break;
                }
            }
        }
        return isSimple;
    }
}
