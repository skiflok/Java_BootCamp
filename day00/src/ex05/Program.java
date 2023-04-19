package ex05;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class Program {

    static {
        String testString =
                "John\nMike\n.\n2 MO\n2 WE\n.\nMike 2 28 NOT_HERE\nJohn 4 9 HERE\nMike 4 9 HERE\n.\n";
        InputStream is = new ByteArrayInputStream(testString.getBytes());
        System.setIn(is);
    }
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        if (!scanner.hasNext()) {
            exitApp();
        }

        String[] students = getStringArrayFromInput();
        String[] schedule = getStringArrayFromInput();
        String[] attendance = getStringArrayFromInput();

        // TODO
        printStringArray(students);
        printStringArray(schedule);
        printStringArray(attendance);

                                //x  y
        int[][] result = new int[31][15];
        // заполнение дат 0 пусто
        for (int i = 0; i < result.length; i++) {
            result[i][0] = i;
        }

        // заполнение дней недели
        int weekDayCode = 3; //mo == 1
        for (int i = 1; i < result.length; i++) {
            if (weekDayCode > 7) {
                weekDayCode = 1;
            }
            result[i][1] = weekDayCode++;
        }


        // OUTPUT
        int testCount = 0;
        for (int y = 0; y < result[0].length; y++) {
            if (y == 0) {
                for (int x = 0; x < result.length; x++) {
                    if (x == 0) {
                        System.out.printf("%10s", "");
                        continue;
                    }
                    if (testCount > 10) break;
                    System.out.printf("%4s %2s %2s|", "time", getWeekDay(result[x][1]), result[x][0]);
                    testCount++;
                }
                System.out.println();
            }

            if (y > 3){
                System.out.printf("%4s\n", result[0][y]);
            }

        }


        scanner.close();
    }

    // 1 = tue

//    Monday (понедельник) - Mo
//    Tuesday (вторник) - Tu
//    Wednesday (среда) - We
//    Thursday (четверг) - Th
//    Friday (пятница) - Fr
//    Saturday (суббота) - Sa
//    Sunday (воскресенье) - Su


    public static void exitApp() {
        scanner.close();
        System.err.print("Illegal Argument");
        System.exit(-1);
    }

    public static String getWeekDay(int nun) {
        String res = null;
        switch (nun) {
            case 1:
                res = "MO";
                break;
            case 2:
                res = "TU";
                break;
            case 3:
                res = "WE";
                break;
            case 4:
                res = "TH";
                break;
            case 5:
                res = "FR";
                break;
            case 6:
                res = "SA";
                break;
            case 7:
                res = "SU";
                break;
        }
        return res;
    }


    public static void printStringArray(String[] arr) {
        for (String str : arr) {
            if (str != null) {
                System.out.println(str);
            }
        }
    }

    public static String[] getStringArrayFromInput() {
        String input;
        byte count = 0;
        String[] temp = new String[10];
        while (count < 10 && !(input = scanner.nextLine()).equals(".")) {
            if (input.length() > 50) {
                exitApp();
            }
            temp[count] = input;
            count++;
        }
        return temp;
    }

    public static String[] split(String str, char delimiter) {
        int delimiterCount = 0;
        char[] chars = str.toCharArray();
        for (char ch : chars) {
            if (ch == ' ') {
                delimiterCount++;
            }
        }
        String[] arr = new String[delimiterCount + 1];
        int i = 0;
        for (char ch : chars) {
            if (ch != ' ') {
                if (arr[i] == null) {
                    arr[i] = "" + ch;
                } else {
                    arr[i] += ch;
                }
            } else {
                i++;
            }
        }
        return arr;
    }
}
