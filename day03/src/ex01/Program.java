package ex01;

public class Program {

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

    private static void run(int count) {
        final Object monitor = new Object();
        Printer egg = new Printer(count, monitor, "Egg");
        Printer hen = new Printer(count, monitor, "Hen");
        egg.start();
        hen.start();
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
