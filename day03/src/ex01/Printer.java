package ex01;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class Printer {

    final Lock lock = new ReentrantLock();

    final int count;

    public Printer(int count) {
        this.count = count;
    }

    public void print(String str) {
        lock.lock();
        try {
            System.out.println(str);
        } finally {
            lock.unlock();
        }
    }
}
