package ex00;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileSignatureDictionary {
    private final Map<String, String> fileSignatureDictionary = new HashMap<>();

    FileSignatureDictionary () {
        fillSignatureDictionary();
    }
    private int maxLengthSignature;

    private void fillSignatureDictionary() {
        String path = "day02/src/ex00/signatures.txt";
        try (Scanner readSignatures = new Scanner(new FileInputStream(path))) {
            String [] tempDictionaryArray;
            while (readSignatures.hasNext()) {
                tempDictionaryArray = readSignatures.nextLine().split(",");
                int spaceCount = tempDictionaryArray[1].split(" ").length - 1;
                if (maxLengthSignature < spaceCount) {
                    maxLengthSignature = spaceCount;
                }
                fileSignatureDictionary.put(tempDictionaryArray[0].trim(), tempDictionaryArray[1].trim());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Файл не найдет");
        }
//        for (Map.Entry<String, String> signature : fileSignatureDictionary.entrySet()) {
//            System.out.println(signature);
//        }
    }

    public Map<String, String> getFileSignatureDictionary() {
        return fileSignatureDictionary;
    }
    public int getMaxLengthSignature() {
        return maxLengthSignature;
    }
}
