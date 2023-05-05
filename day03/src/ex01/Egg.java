package ex01;

public class Egg extends Thread {
    private final int count;

    public Egg(int count) {
        this.count = count;
    }

    @Override
    public void run() {

        synchronized (Program.monitor) {
            try {
                for (int i = 0; i < count; i++) {
                    System.out.println("Egg" + i);
                    Program.monitor.notify();
                    if (i != count - 1) {
                        Program.monitor.wait();
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            finally {
                Program.monitor.notify();
            }
        }
//        int breakCount = 3;
//        while (breakCount < count) {
//            try {
////                Thread.sleep(1000);
//                Program.block.put(breakCount);
//                System.out.println("Egg put " + breakCount);
//                breakCount++;
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
    }
}
