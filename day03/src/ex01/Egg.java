package ex01;

public class Egg extends Thread {
    private int count;

    public Egg(int count) {
        this.count = count;
    }

    @Override
    public void run() {
        int breakCount = 0;
        while (breakCount < count) {
            try {
                Thread.sleep(1000);
                Program.block.put(true);
                System.out.println("Egg " + breakCount);
                breakCount++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
