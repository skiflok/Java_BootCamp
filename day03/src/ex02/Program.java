package ex02;


import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Program {

    public static void main(String[] args) {

        try {
            final HashMap<String, Integer> inputParamsToUse = checkAndGetParam(args);
            int arraySize = inputParamsToUse.get("--arraySize");
            int threadCount = inputParamsToUse.get("--threadsCount");

            System.out.println(inputParamsToUse);

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

    public static HashMap<String, Integer> checkAndGetParam(String[] args) {

        final List<String> validParam = new LinkedList<>(
                Arrays.asList("--arraySize", "--threadsCount")
        );

        final HashMap<String, Integer> inputParamsToUse = new HashMap<>();

        if (args.length != 2) {
            throw new IllegalArgumentException("Неверное количество аргументов");
        }

        String[] param;
        int numericParameter;
        for (String s : args) {
            param = s.split("=");
            if (param.length != 2 || !validParam.contains(param[0])) {
                throw new IllegalArgumentException("Неизвестнные параметры ввода");
            }
            if (inputParamsToUse.containsKey(param[0])) {
                throw new IllegalArgumentException("Параметры ввода дублируются");
            }
            numericParameter = Integer.parseInt(param[1]);
            if (numericParameter <= 0) {
                throw new IllegalArgumentException("Некорректный ввод числового параметра");
            }
            inputParamsToUse.put(param[0], numericParameter);
        }

        return inputParamsToUse;
    }
}
