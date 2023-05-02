package ex01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Program {

    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Неверное количество аргументов");
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

            int[] A = frequencyAnalysisOfDictionary(dictionary, wordsFile1);
            int[] B = frequencyAnalysisOfDictionary(dictionary, wordsFile2);

            double similarity = scalarMultiplicationVectors(A, B) / (euclideanNormVector(A) * euclideanNormVector(B));


            similarity = ((int) (similarity * 100)) / 100.0;

            System.out.printf("Similarity = %.2f%n", similarity);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static double scalarMultiplicationVectors (int [] A, int [] B) {
        double res = 0;
        for (int i = 0; i < A.length; i++) {
            res += (A[i] * B[i]);
        }
        return res;
    }

    private static double euclideanNormVector(int[] arr) {
        double res = 0;
        for (int num : arr) {
            res += num * num;
        }
        res = Math.sqrt(res);
        return res;
    }

    private static int[] frequencyAnalysisOfDictionary(LinkedHashSet<String> dictionary, String[] arr) {
        int[] frequency = new int[dictionary.size()];
        int index = 0;
        int count;
        for (String d : dictionary) {
            count = 0;
            for (String s : arr) {
                if (d.equals(s)) {
                    count++;
                }
            }
            frequency[index] = count;
            index++;
        }
        return frequency;
    }

}