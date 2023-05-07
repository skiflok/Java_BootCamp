package ex02;


import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Program {

    public static void main(String[] args) {

        try {
            CommandLineArguments.fiiArguments(args);
            int arraySize = CommandLineArguments.getParamValue("--arraySize");
            int threadCount = CommandLineArguments.getParamValue("--threadsCount");

            System.out.println(arraySize);
            System.out.println(threadCount);



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
