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

        int[][] calendar = new int[31][2];
        // заполнение дат 0 пусто
        for (int i = 0; i < calendar.length; i++) {
            calendar[i][0] = i;
        }

        // заполнение дней недели
        int weekDayCode = 2; //mo == 1
        for (int i = 1; i < calendar.length; i++) {
            if (weekDayCode > 7) {
                weekDayCode = 1;
            }
            calendar[i][1] = weekDayCode++;
        }


        int clCount = 0;
        for (String str : schedule) {
            if (str != null) {
                clCount++;
            } else {
                break;
            }
        }

        boolean[][] tableClass = new boolean[dayOfWeek + 1][numberOfHours + 1];

        int week;
        int hour;
        for (int i = 0; i < clCount; i++) {
            String[] temp = split(schedule[i], ' ');
            week = getIndexWeek(temp[1]);
            hour = getIndexHour(temp[0]);
            tableClass[week][hour] = true;
        }

        int studentCount = 0;
        for (String str : students) {
            if (str != null) {
                studentCount++;
            } else {
                break;
            }
        }

        int attendanceCount = 0;
        for (String str : attendance) {
            if (str != null) {
                attendanceCount++;
            } else {
                break;
            }
        }

        int[][][] studentsVisiting = new int[studentCount][dayInSeptember + 1][numberOfHours + 1];

        int day;
        int nane;
        int status;
        for (int i = 0; i < attendanceCount; i++) {
            String[] temp = split(attendance[i], ' ');
            nane = getIndexName(students, temp[0]);
            hour = getIndexHour(temp[1]);
            day = Integer.parseInt(temp[2]);
            status = getStatus(temp[3]);
            studentsVisiting[nane][day][hour] = status;
        }

        for (int line = 0; line < studentCount + 1; line++) {
            for (int day_ = 0; day_ < dayInSeptember + 1; day_++) {
                if (line == 0 && day_ == 0) {
                    System.out.printf("%10s", "");
                    continue;
                } else if (day_ == 0) {
                    System.out.printf("%10s", students[line - 1]);
                }
                for (int hour_ = 1; hour_ < numberOfHours; hour_++) {
                    if (tableClass[calendar[day_][1]][hour_]) {
                        if (line == 0) {
                            System.out.printf("%4s %2s %2s|", hour_ + ":00", getWeekDay(calendar[day_][1]), day_);
                        } else {
                            if (studentsVisiting[line - 1][day_][hour_] == 0) {
                                System.out.printf("%10s|", "");
                            } else {
                                System.out.printf("%10s|", studentsVisiting[line - 1][day_][hour_]);
                            }
                        }
                    }
                }
            }
            System.out.println();
        }
        scanner.close();
    }
    public static void exitApp() {
        scanner.close();
        System.err.print("Illegal Argument");
        System.exit(-1);
    }

    public static int getStatus(String status) {
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

    public static int getIndexName(String[] students, String name) {
        int indexName = 0;
        for (int i = 0; i < students.length; i++) {
            if (students[i].equals(name)) {
                indexName = i;
                break;
            }
        }
        return indexName;
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
