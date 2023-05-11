package ex03;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public class MyBlockingQueue {

    private final Queue<DownloadTask> downloadTasksQueue;

    public MyBlockingQueue () {
        downloadTasksQueue = new LinkedList<>();
    }

    public synchronized void put (DownloadTask downloadTask) {
        downloadTasksQueue.offer(downloadTask);
    }

    public synchronized DownloadTask take () {
        return downloadTasksQueue.poll();
    }

    public void forEach(Consumer<? super DownloadTask> action) {
        downloadTasksQueue.forEach(action);
    }

    public boolean isEmpty() {
        return downloadTasksQueue.isEmpty();
    }
}
