package ex01;

public class Printer extends  Thread{
    private final int count;

    private final Object monitor;

    private final String printString;

    public Printer(int count, Object monitor, String printString) {
        this.count = count;
        this.monitor = monitor;
        this.printString = printString;
    }


    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            synchronized (monitor) {
                monitor.notify();
                System.out.println(printString);
                try {
                    if (i != count - 1) {
                        monitor.wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
