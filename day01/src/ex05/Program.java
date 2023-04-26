package ex05;

public class Program {

    public static void main(String[] args) {
        boolean dev = args.length > 0 && args[0].equals("--profile=dev");
        Menu menu = new Menu(dev);
        menu.run();
    }
}
