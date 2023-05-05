package ex00;

public class Program {

    public static void main(String[] args) {

        int param = checkAndGetParam(args);

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.printf("%s i = %d\n", Thread.currentThread().getName(), i);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.printf("%s i = %d\n", Thread.currentThread().getName(), i);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        for (int i = 0; i < 5; i++) {
            System.out.printf("%s i = %d\n", Thread.currentThread().getName(), i);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

//        Hen hen = new Hen();
//        Egg egg = new Egg();
//        hen.start();
//        egg.start();

    }

    public static int checkAndGetParam(String[] args) {
        int param = 0;
        if (args.length != 1) {
            System.out.println("Неверное количество аргументов");
            System.exit(-1);
        }

        String[] inputParam = args[0].split("=");
        if (inputParam.length != 2 || !inputParam[0].equals("--count")) {
            System.out.println("Неизвестнные параметры ввода");
            System.exit(-1);
        }

        int count;
        try {
            count = Integer.parseInt(inputParam[1]);
            if (count <= 0) {
                System.out.println("Некорректный ввод числового параметра");
                System.exit(-1);
            }
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ввод числового параметра");
            System.exit(-1);
        }

        return param;
    }

}
