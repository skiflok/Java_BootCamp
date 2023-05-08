package ex02;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
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

            System.out.println("min = " + min);
            System.out.println("max = " + max);
            System.out.println("Sum = " + sum);

            ExecutorService service = Executors.newFixedThreadPool(threadCount);
            List<Future<Integer>> tasks = new LinkedList<>();
            int elementInArray = arraySize / threadCount;
            System.out.println("elementInArray = " + elementInArray);
            int startIndex = 0;
            int endIndex = 0;

            for (int i = 0; i < threadCount; i++) {
                if (i != threadCount - 1) {
                    startIndex = elementInArray * i;
                    endIndex = elementInArray + elementInArray * i;
                    System.out.printf("startIndex = %d endIndex = %d\n", startIndex, endIndex);
                } else {
                    startIndex = endIndex;
                    endIndex = arraySize;
                }
                int finalStartIndex = startIndex;
                int finalEndIndex = endIndex;
                tasks.add((service.submit(() -> Arrays.stream(arr, finalStartIndex, finalEndIndex).sum())));
            }

            service.shutdown();

            for (Future<Integer> task : tasks) {
                System.out.println(task.get());
            }

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
