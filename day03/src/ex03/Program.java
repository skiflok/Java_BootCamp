package ex03;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Program {

    public static void main(String[] args) {

        try {
            CommandLineArguments.fiiArguments(args);
            int threadsCount = CommandLineArguments.getParamValue("--threadsCount");

//            String file = "./resources/files_url.txt";
//            String downloadDirectory = "./resources/download/";
            String filesUrl = "./day03/src/ex03/resources/files_url.txt";
            String downloadDirectory = "./day03/src/ex03/resources/download/";

            run(downloadDirectory, filesUrl, threadsCount);

        } catch (NumberFormatException e) {
            System.out.printf("Неверный числовой параметр %s\n", e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.printf("Файл не найден %s\n", e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static void run(String downloadDirectory, String filesUrl, int threadsCount) throws IOException {

        Path downloadDirectoryPath = Paths.get(downloadDirectory);

        Map<String, String> urls = readFileWithResourceToDownload(filesUrl);
//        BlockingQueue<DownloadTask> downloadTasksQueue = new LinkedBlockingQueue<>(50);
        MyBlockingQueue myBlockingQueue = new MyBlockingQueue();

        for (Map.Entry<String, String> task : urls.entrySet()) {
//                downloadTasksQueue.add(new DownloadTask(task.getKey(), task.getValue()));
            myBlockingQueue.put(new DownloadTask(task.getKey(), task.getValue()));
        }

        if (!Files.exists(downloadDirectoryPath)) {
            Files.createDirectory(downloadDirectoryPath);
        }

//            List<Thread> downloaderThreads = new ArrayList<>();
        for (int i = 1; i < threadsCount + 1; i++) {
            Thread thread = new Thread(new UrlFileDownloader(myBlockingQueue, downloadDirectory), "Thread-" + i);
//                downloaderThreads.add(thread);
            thread.start();
        }

    }

    private static Map<String, String> readFileWithResourceToDownload(String filesUrl) {

        Map<String, String> urls = null;

        try (Stream<String> stream = Files.lines(Paths.get(filesUrl))) {
            urls = stream.map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toMap(
                            key -> {
                                String[] parts = key.split(" ");
                                if (parts.length != 2) {
                                    throw new IllegalArgumentException("Line does not have two parts: " + key);
                                }
                                return parts[0];
                            }, // создаем ключ из первого слова
                            value -> value.split(" ")[1], // создаем значение из оставшейся части строки
                            (v1, v2) -> v1, // функция для обработки дубликатов ключей
                            LinkedHashMap::new // указываем тип Map, чтобы сохранить порядок элементов
                    ));

        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return urls;
    }

}
