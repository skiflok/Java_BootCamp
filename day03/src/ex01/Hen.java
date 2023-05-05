package ex01;

public class Hen extends Thread {
    private final int count;

    public Hen(int count) {
        this.count = count;
    }

    @Override
    public void run() {

        synchronized (Program.monitor) {
            for (int i = 0; i < count; i++) {
                try {
                    System.out.println("Hen"+ i);
                    Program.monitor.notify();
                    if (i != count - 1) {
                        Program.monitor.wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
//        int breakCount = 0;
//        while (breakCount < count - 1) {
//            try {
//                breakCount = Program.block.take();
//                Thread.sleep(10);
//                System.out.println("Hen take " + breakCount);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
    }
}
