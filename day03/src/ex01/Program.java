package ex01;

import java.util.concurrent.LinkedBlockingQueue;

public class Program {

    final static LinkedBlockingQueue<Integer> block = new LinkedBlockingQueue<>(1);
    final static Object monitor = new Object();

    public static void main(String[] args) {
        try {
            int count = checkAndGetParam(args);
            run(count);
        } catch (NumberFormatException e) {
            System.out.printf("Неверный числовой параметр %s\n", e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void run(int count) throws InterruptedException {
//        Egg egg = new Egg(count);
//        Hen hen = new Hen(count);
//        egg.start();
//        hen.start();

        Printer printer = new Printer(5);
        Runnable egg = () -> {
            for (int i = 0; i < printer.count; i++) {
                printer.print("Egg");
            }
        };
        Runnable hen = () -> {
            for (int i = 0; i < printer.count; i++) {
                printer.print("Hen");
            }
        };

        Thread eggThread = new Thread(egg);
        Thread henThread = new Thread(hen);

        eggThread.start();
        henThread.start();
        eggThread.join();
        henThread.join();


    }

    public static int checkAndGetParam(String[] args) {

        if (args.length != 1) {
            throw new IllegalArgumentException("Неверное количество аргументов");
        }

        String[] inputParam = args[0].split("=");
        if (inputParam.length != 2 || !inputParam[0].equals("--count")) {
            throw new IllegalArgumentException("Неизвестнные параметры ввода");
        }

        int count = Integer.parseInt(inputParam[1]);
        if (count <= 0) {
            throw new IllegalArgumentException("Некорректный ввод числового параметра");
        }
        return count;
    }
}
