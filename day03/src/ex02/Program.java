package ex02;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
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

            run(arraySize, threadCount);

        } catch (NumberFormatException e) {
            System.out.printf("Неверный числовой параметр %s\n", e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void run(int arraySize, int threadCount) throws ExecutionException, InterruptedException {

        int[] arr = new Random().ints(arraySize, -1000, 1001).toArray();

//            int min = Arrays.stream(arr).min().getAsInt();
//            int max = Arrays.stream(arr).max().getAsInt();
//            System.out.println("min = " + min);
//            System.out.println("max = " + max);

        long start = System.currentTimeMillis();
        long sum = simpleSum(arr);
        long end = System.currentTimeMillis();
        System.out.println("Время выполнения: " + (end - start) + " мс");

        System.out.println("Sum = " + sum);

        start = System.currentTimeMillis();
        sum = threadSum(arr, threadCount);
        end = System.currentTimeMillis();
        System.out.println("Время выполнения: " + (end - start) + " мс");

        System.out.println("sum by threads = " + sum);

    }

    private static int simpleSum(int[] arr) {
        return Arrays.stream(arr).sum();
    }

    private static int threadSum(int[] arr, int threadCount) throws ExecutionException, InterruptedException {
        int sum;

        ExecutorService service = Executors.newFixedThreadPool(threadCount);
        List<Future<Integer>> tasks = new LinkedList<>();
        int elementInArray = arr.length / threadCount;
        int startIndex;
        int endIndex = 0;

        for (int i = 0; i < threadCount; i++) {
            if (i != threadCount - 1) {
                startIndex = elementInArray * i;
                endIndex = elementInArray + elementInArray * i;
            } else {
                startIndex = endIndex;
                endIndex = arr.length;
            }
            int finalStartIndex = startIndex;
            int finalEndIndex = endIndex;
            int finalI = i;
            tasks.add((service.submit(() -> {
                int threadSum = Arrays.stream(arr, finalStartIndex, finalEndIndex).sum();
                System.out.printf("Thread %d: from %d to %d sum is %d\n", finalI + 1, finalStartIndex, finalEndIndex, threadSum);
                return threadSum;
            })));
        }
        service.shutdown();
        sum = 0;
        for (Future<Integer> task : tasks) {
            sum += task.get();
        }
        return sum;
    }

}
