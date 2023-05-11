package ex03;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;

public class UrlFileDownloader implements Runnable {

//    private final BlockingQueue<DownloadTask> downloadTasksQueue;
    private final MyBlockingQueue downloadTasksQueue;

    private final String downloadDirectory;
    DownloadTask downloadTask;


    public UrlFileDownloader(MyBlockingQueue downloadTasksQueue, String downloadDirectory) {
        this.downloadTasksQueue = downloadTasksQueue;
        this.downloadDirectory = downloadDirectory;

    }

    @Override
    public void run() {
        String fileName;
        while (!downloadTasksQueue.isEmpty()) {
            try {
                downloadTask = downloadTasksQueue.take();
                fileName = downloadTask.getFilename();
                BufferedOutputStream fileWriter = new BufferedOutputStream(Files.newOutputStream(Paths.get(
                        downloadDirectory +
                                "ID=" +
                                downloadTask.getId() +
                                "___" +
                                fileName)));
                BufferedInputStream urlReader = new BufferedInputStream(
                        new URL(downloadTask.getUrl()).openStream());

                byte[] dataBuffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = urlReader.read(dataBuffer, 0, 1024)) != -1) {
                    fileWriter.write(dataBuffer, 0, bytesRead);
                }

                fileWriter.flush();
                fileWriter.close();
                urlReader.close();
            } catch (URISyntaxException | IOException e) {
                throw new RuntimeException(e);
            }

            System.out.printf("%s start download file number %s\n", Thread.currentThread().getName(), downloadTask.getId());

            System.out.printf("%s finish download file number %s\n", Thread.currentThread().getName(), downloadTask.getId());
        }
    }
}
