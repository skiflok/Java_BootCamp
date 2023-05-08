package ex03;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CommandLineArguments {

    final private static List<String> validParam = new LinkedList<>();

    static {
        validParam.add("--threadsCount");
    }

    final private static HashMap<String, Integer> inputParamsToUse = new HashMap<>();

    public static void fiiArguments(String[] args) {

        if (args.length != validParam.size()) {
            throw new IllegalArgumentException("Неверное количество аргументов");
        }
        String[] param;
        int numericParameter;
        for (String s : args) {
            param = s.split("=");
            if (param.length != 2 || !validParam.contains(param[0])) {
                throw new IllegalArgumentException("Неизвестные параметры ввода");
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
    }

    public static int getParamValue(String key) {
        if (inputParamsToUse.containsKey(key)) {
            return inputParamsToUse.get(key);
        } else throw new IllegalArgumentException("Неверный запрос параметров");
    }

}

