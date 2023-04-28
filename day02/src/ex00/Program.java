package ex00;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

public class Program {

    static Map<String, String>   fileSignatureDictionary = new HashMap<>();
    public static void main(String[] args) {

        String currentDirectory = System.getProperty("user.dir"); //del
        System.out.println("Текущая директория: " + currentDirectory); //del

        //TODO заполнить файл сигнатурами
        //TODO открыть файл
        //TODO определить сигнатуру
        //TODO вывести статус команду
        //TODO записать в файл фип файла

        int maxLength = fillSignatureDictionary();
        System.out.println(maxLength);

        String startFileBytes = getStartByteToString(maxLength);

        System.out.println(startFileBytes);


        String result = null;
        for (Map.Entry<String, String> mapEntry:
             fileSignatureDictionary.entrySet()) {
            if (startFileBytes.startsWith(mapEntry.getValue())) {
                System.out.println(mapEntry.getKey());
                result = mapEntry.getKey() + "\n";
            }
        }


        try (FileOutputStream fos = new FileOutputStream("day02/src/ex00/result.txt", true)) {
            assert result != null;
            byte[] bytes = result.getBytes();
            fos.write(bytes);
            System.out.println("Data has been written to the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private static String getStartByteToString(int maxLength) {
        File file = new File("day02/src/ex00/test.png");
        String startFileBytes = null;
        try (FileInputStream fis = new FileInputStream(file)) {
            StringBuilder stringBuilder = new StringBuilder();
            int count = 0;
            int value;
            while ((value = fis.read()) != -1) {
                count++;
                if (count > maxLength) break;
                stringBuilder.append(String.format("%02X ", value));
            }
            startFileBytes = stringBuilder.toString().trim();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return startFileBytes;
    }

    private static int fillSignatureDictionary() {
        String path = "day02/src/ex00/signatures.txt";
//        Map<String, String>   fileSignatureDictionary = new HashMap<>();
        int maxLength = 0;
        try (Scanner readSignatures = new Scanner(new FileInputStream(path))) {
            String [] tempDictionaryArray;
            while (readSignatures.hasNext()) {
                tempDictionaryArray = readSignatures.nextLine().split(",");
                int spaceCount = tempDictionaryArray[1].split(" ").length - 1;
                if (maxLength < spaceCount) {
                    maxLength = spaceCount;
                }
                fileSignatureDictionary.put(tempDictionaryArray[0].trim(), tempDictionaryArray[1].trim());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Файл не найдет");
        }
        for (Map.Entry<String, String> signature : fileSignatureDictionary.entrySet()) {
            System.out.println(signature);
        }
        return maxLength;
    }
}
