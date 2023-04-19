package ex05;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
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


        int[][] calendar = new int[31][2];
        // заполнение дат 0 пусто
        for (int i = 0; i < calendar.length; i++) {
            calendar[i][0] = i;
        }

        // заполнение дней недели
        int weekDayCode = 3; //mo == 1
        for (int i = 1; i < calendar.length; i++) {
            if (weekDayCode > 7) {
                weekDayCode = 1;
            }
            calendar[i][1] = weekDayCode++;
        }

        System.out.println("############################");

        System.out.println("----Parsing schedule----");

        int clCount = 0;
        for (String str : schedule) {
            if (str != null) {
                clCount++;
            } else {
                break;
            }
        }

        System.out.println("clCount = " + clCount);

        boolean[][] tableClass = new boolean[7 + 1][5 + 1];

        int week = 0;
        int hour = 0;
        for (int i = 0; i < clCount; i++) {
            String[] temp = split(schedule[i], ' ');
            week = getIndexWeek(temp[1]);
            hour = getIndexHour(temp[0]);
            tableClass[week][hour] = true;
            System.out.println(week + " " + hour);
            System.out.println(Arrays.toString(temp));
        }

        for (int i = 0; i < tableClass.length; i++) {
            for (int j = 0; j < tableClass[0].length; j++) {
                if (tableClass[i][j]) {
                    System.out.print("x");
                } else {
                    System.out.print("o");
                }
            }
            System.out.println();
        }

        System.out.println("----Parsing schedule----");


        //y  x
        int[][] result = new int[31][15];
        // заполнение дат 0 пусто
        for (int i = 0; i < result.length; i++) {
            result[i][0] = i;
        }
        // заполнение дней недели
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

            if (y > 3) {
//                System.out.printf("%4s\n", result[0][y]);
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

    public static int getIndexHour(String week) {
        int indexHour = 0;
        switch (week) {
            case "1":
                indexHour = 1;
                break;
            case "2":
                indexHour = 2;
                break;
            case "3":
                indexHour = 3;
                break;
            case "4":
                indexHour = 4;
                break;
            case "5":
                indexHour = 5;
                break;
        }
        return indexHour;
    }

    public static int getIndexWeek(String week) {
        int indexWeek = 0;
        switch (week) {
            case "MO":
                indexWeek = 1;
                break;
            case "TU":
                indexWeek = 2;
                break;
            case "WE":
                indexWeek = 3;
                break;
            case "TH":
                indexWeek = 4;
                break;
            case "FR":
                indexWeek = 5;
                break;
            case "SA":
                indexWeek = 6;
                break;
            case "SU":
                indexWeek = 7;
                break;
        }


        return indexWeek;
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
