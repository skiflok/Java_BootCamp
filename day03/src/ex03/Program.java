package ex03;

public class Program {

    public static void main(String[] args) {

        try {
            CommandLineArguments.fiiArguments(args);
            int threadsCount = CommandLineArguments.getParamValue("--threadsCount");
            run(threadsCount);

        } catch (NumberFormatException e) {
            System.out.printf("Неверный числовой параметр %s\n", e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void run (int threadsCount) {
        System.out.println("threadsCount = " + threadsCount);
    }

}
