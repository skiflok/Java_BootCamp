package ex01;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        int count = 1;
        boolean isSimple = true;
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        if (num <= 1) {
            System.err.println("IllegalArgument");
            System.exit(-1);
        } else {
            for (int i = 2; i * i <= num; i++) {
                if (num % i == 0) {
                    isSimple = false;
                    break;
                }
                ++count;
            }
            System.out.println(isSimple + " " + count);
        }
        scanner.close();
    }
}
