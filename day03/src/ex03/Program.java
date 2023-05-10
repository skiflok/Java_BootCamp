package ex03;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Program {

    public static void main(String[] args) {

        try {
            CommandLineArguments.fiiArguments(args);
            int threadsCount = CommandLineArguments.getParamValue("--threadsCount");

            String file = "./resources/files_url.txt";
            Path filePath = Paths.get(file);

            Map<String, String> urls = readFileWithResourceToDownload(file);

            BlockingQueue<DownloadTask> downloadTasks = new LinkedBlockingQueue<>();

//            for (Map.Entry<String, String> entry: urls.entrySet()) {
//                System.out.println(entry);
//            }

            String downloadDirectory = "./resources/download/";
            Path downloadDirectoryPath = Paths.get(downloadDirectory);

            if (Files.exists(downloadDirectoryPath)) {
                System.out.println("exist");
            } else {
                System.out.println("not exist");
                System.out.println("directory create");
                Files.createDirectory(downloadDirectoryPath);
            }

            List<Thread> downloaderThreads = new ArrayList<>();
            for (int i = 0; i < threadsCount; i++) {
                Thread thread = new Thread(new UrlFileDownloader());
                downloaderThreads.add(thread);
                thread.start();
            }



            BufferedOutputStream fileWriter = new BufferedOutputStream(
                    new FileOutputStream(downloadDirectory + "11192eba63f6f3aa591d3263fdb66bd5.jpg"));

            BufferedInputStream urlReader = new BufferedInputStream(
                    new URL("https://i.pinimg.com/originals/11/19/2e/11192eba63f6f3aa591d3263fdb66bd5.jpg").openStream());

            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = urlReader.read(dataBuffer, 0, 1024)) != -1) {
                fileWriter.write(dataBuffer, 0, bytesRead);
            }

            fileWriter.flush();
            fileWriter.close();
            urlReader.close();

            run(threadsCount);

        } catch (NumberFormatException e) {
            System.out.printf("Неверный числовой параметр %s\n", e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.printf("Файл не найден %s\n", e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static void run (int threadsCount) {
//        System.out.println("threadsCount = " + threadsCount);
    }

    private static Map<String, String> readFileWithResourceToDownload(String path) {

        Map<String, String> urls = null;

        try(Stream<String> stream = Files.lines(Paths.get(path))) {
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
