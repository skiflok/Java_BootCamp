package ex01;

public class Egg extends Thread {
    private final int count;

    private final Object monitor;

    public Egg(int count, Object monitor) {
        this.count = count;
        this.monitor = monitor;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            synchronized (monitor) {
                monitor.notify();
                System.out.println("Egg");
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
