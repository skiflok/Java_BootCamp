package ex02;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Program {

    public static void main(String[] args) {

        try {
            CommandLineArguments.fiiArguments(args);
            int arraySize = CommandLineArguments.getParamValue("--arraySize");
            int threadCount = CommandLineArguments.getParamValue("--threadsCount");

            if (arraySize > 2_000_000 || threadCount > arraySize) {
                throw new RuntimeException("количество потоков больше количества элементов массива или\n" +
                        "максимальное количество элементов массива больше 2 000 000.");
            }

            int[] arr = new Random().ints(arraySize, 1, 2).toArray();

            int min = Arrays.stream(arr).min().getAsInt();
            int max = Arrays.stream(arr).max().getAsInt();

            long start = System.nanoTime();
            long sum = Arrays.stream(arr).sum();
            long end = System.nanoTime();
            System.out.println("Время выполнения: " + (end - start) + " нс");

            System.out.println(min);
            System.out.println(max);
            System.out.println(sum);

//
//            for (int i = 0; i < threadCount; i++) {
//
//            }

            ExecutorService service = Executors.newFixedThreadPool(threadCount);
            Future<Integer> task = service.submit(new Calculator(arr));

            service.shutdown();

            System.out.println(task.get());

//            run(count);
        } catch (NumberFormatException e) {
            System.out.printf("Неверный числовой параметр %s\n", e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void run(int count) {
        final Object monitor = new Object();
    }
}
