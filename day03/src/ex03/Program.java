package ex03;

import javax.swing.plaf.basic.BasicButtonUI;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Program {

    public static void main(String[] args) {

        try {
            CommandLineArguments.fiiArguments(args);
            int threadsCount = CommandLineArguments.getParamValue("--threadsCount");

            String file = "./files_url.txt";
            Path filePath = Paths.get(file);
            System.out.println(filePath);


            BufferedReader reader = new BufferedReader(new FileReader(file));
            HashMap<String, String> urls = new HashMap<>();
            String [] temp;
            while (reader.ready()) {
                temp = reader.readLine().split(" ");
                if (temp.length != 2) throw new IllegalArgumentException("Bad args");
                urls.put(temp[0], temp[1]);
            }



            for (Map.Entry<String, String> entry: urls.entrySet()) {
                System.out.println(entry);
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
        System.out.println("threadsCount = " + threadsCount);
    }

}
