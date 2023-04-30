package ex00;

import java.io.*;
import java.util.Map;
import java.util.Scanner;


public class Program {

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);
        String signatureFilePath = "day02/src/ex00/signatures.txt";
        String resultPath = "day02/src/ex00/result.txt";

        FileSignatureDictionary fileSignatureDictionary = new FileSignatureDictionary();
        fileSignatureDictionary.fillSignatureDictionary(signatureFilePath);

        String filePath;
        int maxLength;
        String startFileBytes;
        String result;
        while (!(filePath = scanner.nextLine()).equals("42")) {
            result = null;
            try {
                maxLength = fileSignatureDictionary.getMaxLengthSignature();
                startFileBytes = getStartByteFileToString(filePath, maxLength);
                result = defineContentType(fileSignatureDictionary, startFileBytes);
            } catch (Exception ignore) {
            }
            if (result == null) {
                System.out.println("UNDEFINED");
            } else {
                writeResult(resultPath, result);
                System.out.println("PROCESSED");
            }

        }
        scanner.close();
    }


    private static String defineContentType (FileSignatureDictionary fileSignatureDictionary, String startFileBytes) {
        String result = null;
        for (Map.Entry<String, String> mapEntry:
                fileSignatureDictionary.getFileSignatureDictionary().entrySet()) {
            if (startFileBytes.startsWith(mapEntry.getValue())) {
                System.out.println(mapEntry.getKey());
                result = mapEntry.getKey() + "\n";
            }
        }
        return result;
    }

    private static void writeResult(String resultPath, String result) {
        try (FileOutputStream fos = new FileOutputStream(resultPath, true)) {
            byte[] bytes = result.getBytes();
            fos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getStartByteFileToString(String filePath, int maxLength) {
        File file = new File(filePath);
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
            System.out.println("File not found");
        }
        return startFileBytes;
    }
}

// "day02/src/ex00/test.png"