package ex05;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class Program {

    static {
        String testString =
                "John\nMike\n.\n2 MO\n4 WE\n.\nMike 2 28 NOT_HERE\nJohn 4 9 HERE\nMike 4 9 HERE\n.\n";
        InputStream is = new ByteArrayInputStream(testString.getBytes());
        System.setIn(is);
    }

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        if (!scanner.hasNext()) {
            exitApp();
        }

        int dayInSeptember = 30;
        int numberOfHours = 5;
        int dayOfWeek = 7;

        String[] students = getStringArrayFromInput();
        String[] schedule = getStringArrayFromInput();
        String[] attendance = getStringArrayFromInput();

        int[] calendar = new int[31];

        fillCalendar(calendar);

        boolean[][] tableClass = new boolean[dayOfWeek + 1][numberOfHours + 1];

        fillTableClass(tableClass, schedule);

        int[][][] studentsVisiting = new int[sizeOfArray(students)][dayInSeptember + 1][numberOfHours + 1];

        fillStudentsVisiting(studentsVisiting, attendance, students);

        for (int line = 0; line < sizeOfArray(students) + 1; line++) {
            for (int day = 0; day < dayInSeptember + 1; day++) {
                if (line == 0 && day == 0) {
                    System.out.printf("%10s", "");
                    continue;
                } else if (day == 0) {
                    System.out.printf("%10s", students[line - 1]);
                }
                for (int hour = 1; hour < numberOfHours; hour++) {
                    if (tableClass[calendar[day]][hour]) {
                        if (line == 0) {
                            System.out.printf("%1d:00%3s%3d|", hour, getWeekDay(calendar[day]), day);
                        } else {
                            if (studentsVisiting[line - 1][day][hour] == 0) {
                                System.out.printf("%10s|", "");
                            } else {
                                System.out.printf("%10d|", studentsVisiting[line - 1][day][hour]);
                            }
                        }
                    }
                }
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void fillStudentsVisiting(int[][][] studentsVisiting, String[] attendance, String[] students) {

        int day;
        int nane;
        int status;
        int hour;
        for (int i = 0; i < sizeOfArray(attendance); i++) {
            String[] temp = split(attendance[i], ' ');
            nane = getIndexName(students, temp[0]);
            hour = getIndexHour(temp[1]);
            day = Integer.parseInt(temp[2]);
            status = getStatus(temp[3]);
            studentsVisiting[nane][day][hour] = status;
        }
    }
    private static void fillTableClass (boolean[][] tableClass, String [] schedule) {
        int week;
        int hour;
        for (int i = 0; i < sizeOfArray(schedule); i++) {
            String[] temp = split(schedule[i], ' ');
            week = getIndexWeek(temp[1]);
            hour = getIndexHour(temp[0]);
            tableClass[week][hour] = true;
        }
    }
    private static void fillCalendar (int [] calendar) {
        int weekDayCode = 2;
        for (int i = 1; i < calendar.length; i++) {
            if (weekDayCode > 7) {
                weekDayCode = 1;
            }
            calendar[i] = weekDayCode++;
        }
    }
    private static int sizeOfArray (String [] arr) {
        int count = 0;
        for (String str : arr) {
            if (str != null) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }
    private static void exitApp() {
        scanner.close();
        System.err.print("Illegal Argument");
        System.exit(-1);
    }
    private static int getStatus(String status) {
        int indexStatus = 0;
        switch (status) {
            case "HERE":
                indexStatus = 1;
                break;
            case "NOT_HERE":
                indexStatus = -1;
                break;
        }
        return indexStatus;
    }
    private static int getIndexName(String[] students, String name) {
        int indexName = 0;
        for (int i = 0; i < students.length; i++) {
            if (students[i].equals(name)) {
                indexName = i;
                break;
            }
        }
        return indexName;
    }
    private static int getIndexHour(String week) {
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
    private static int getIndexWeek(String week) {
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
    private static String getWeekDay(int nun) {
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
    private static String[] getStringArrayFromInput() {
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
    private static String[] split(String str, char delimiter) {
        int delimiterCount = 0;
        char[] chars = str.toCharArray();
        for (char ch : chars) {
            if (ch == delimiter) {
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
