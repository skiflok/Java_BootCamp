package ex01;

public class Hen extends Thread {
    private final int count;

    public Hen(int count) {
        this.count = count;
    }

    @Override
    public void run() {
        int breakCount = 0;
        while (breakCount < count) {
            try {
                Program.block.take();
                System.out.println("Hen " + breakCount);
                breakCount++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
