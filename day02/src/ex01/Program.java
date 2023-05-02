package ex01;

import java.io.*;
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
            String[] wordsFile1 = file1.split("\\W+");
            String[] wordsFile2 = file2.split("\\W+");

            LinkedHashSet<String> dictionary = Arrays.stream(wordsFile1).collect(Collectors.toCollection(LinkedHashSet::new));
            dictionary.addAll(Arrays.stream(wordsFile2).collect(Collectors.toSet()));

            createDictionaryFile(dictionary);

            int[] A = frequencyAnalysisOfDictionary(dictionary, wordsFile1);
            int[] B = frequencyAnalysisOfDictionary(dictionary, wordsFile2);

            double similarity = scalarMultiplicationVectors(A, B) / (euclideanNormVector(A) * euclideanNormVector(B));

            similarity = ((int) (similarity * 100)) / 100.0;

            System.out.printf("Similarity = %.2f%n", similarity);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void createDictionaryFile (Set<String> dictionary) {

        File outputFile = new File("day02/src/ex01/dictionary.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (String word : dictionary) {
                writer.write(word);
                writer.newLine();
            }
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