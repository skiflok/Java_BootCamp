package ex01;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        int count = 0;
        boolean isSimple = true;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите число для проверки");
        int num = scanner.nextInt();
        if (num <= 1) {
            System.out.println("IllegalArgument");

        } else {
            for (int i = 2; i < num; i++) {
                ++count;
                if (num % i == 0) {
                    isSimple = false;
                    break;
                }
            }
            System.out.println("Количество иттераций = " + count);
            System.out.println(isSimple);
        }

        scanner.close();

    }
}
