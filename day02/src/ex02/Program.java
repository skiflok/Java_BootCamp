package ex02;

public class Program {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Неверное количество аргументов");
            return;
        }

        String path = args[0];

        FileManager fileManager = new FileManager(path);

        System.out.println(fileManager.getCurrentDirectory());

    }


}
