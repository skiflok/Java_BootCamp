package ex01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Program {

    public static void main(String[] args) {

        if (args.length != 2) {
            return;
        }


        try (BufferedReader fr0 = new BufferedReader(new FileReader(args[0]));
             BufferedReader fr1 = new BufferedReader(new FileReader(args[1]))) {

            String file1 = fr0.lines().collect(Collectors.joining(" "));
            String file2 = fr1.lines().collect(Collectors.joining(" "));
            String[] wordsFile1 = file1.split("\\p{Punct}?[\\s]+");
            String[] wordsFile2 = file2.split("\\p{Punct}?[\\s]+");
            LinkedHashSet<String> dictionary = Arrays.stream(wordsFile1).collect(Collectors.toCollection(LinkedHashSet::new));
            dictionary.addAll(Arrays.stream(wordsFile2).collect(Collectors.toSet()));
            int[] A = new int[dictionary.size()];
            int[] b = new int[dictionary.size()];

            int count = 0;
            for (String d : dictionary) {


                count++;
            }


            System.out.println(dictionary);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}