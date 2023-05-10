package ex03;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Program {

    public static void main(String[] args) {

        try {
            CommandLineArguments.fiiArguments(args);
            int threadsCount = CommandLineArguments.getParamValue("--threadsCount");

            String file = "./files_url.txt";
            Path filePath = Paths.get(file);

            HashMap<String, String> urls = readFileWithResourceToDownload(file);

            for (Map.Entry<String, String> entry: urls.entrySet()) {
//                System.out.println(entry);
            }

            String downloadDirectory = "./download/";
            Path downloadDirectoryPath = Paths.get(downloadDirectory);

            if (Files.exists(downloadDirectoryPath)) {
                System.out.println("exist");
            } else {
                System.out.println("not exist");
                System.out.println("directory create");
                Files.createDirectory(downloadDirectoryPath);
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

    private static HashMap<String, String> readFileWithResourceToDownload(String path) {
        HashMap<String, String> urls = new LinkedHashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String [] temp;
            while (reader.ready()) {
                temp = reader.readLine().split(" ");
                if (temp.length != 2) throw new IllegalArgumentException("Bad args");
                urls.put(temp[0], temp[1]);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return urls;
    }

}
