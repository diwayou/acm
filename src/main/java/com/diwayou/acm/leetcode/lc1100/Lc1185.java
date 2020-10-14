package com.diwayou.acm.leetcode.lc1100;

import java.time.LocalDate;

/**
 * https://leetcode-cn.com/problems/day-of-the-week/
 * <p>
 * 给你一个日期，请你设计一个算法来判断它是对应一周中的哪一天。
 * 输入为三个整数：day、month 和year，分别表示日、月、年。
 * 您返回的结果必须是这几个值中的一个{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"}。
 */
public class Lc1185 {

    public static void main(String[] args) {
        System.out.println(new Lc1185().dayOfTheWeek(29, 9, 2019));
    }

    public String dayOfTheWeek1(int day, int month, int year) {
        LocalDate d = LocalDate.of(year, month, day);

        String[] week = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        return week[d.getDayOfWeek().ordinal()];
    }

    private static final int DAYS_PER_CYCLE = 146097;
    /**
     * The number of days from year zero to year 1970.
     * There are five 400 year cycles from year zero to 2000.
     * There are 7 leap years from 1970 to 2000.
     */
    private static final long DAYS_0000_TO_1970 = (DAYS_PER_CYCLE * 5L) - (30L * 365L + 7L);

    public String dayOfTheWeek(int day, int month, int year) {
        boolean isLeapYear = ((year & 3) == 0) && ((year % 100) != 0 || (year % 400) == 0);
        long y = year;
        long m = month;
        long total = 0;
        total += 365 * y;
        if (y >= 0) {
            total += (y + 3) / 4 - (y + 99) / 100 + (y + 399) / 400;
        } else {
            total -= y / -4 - y / -100 + y / -400;
        }
        total += ((367 * m - 362) / 12);
        total += day - 1;
        if (m > 2) {
            total--;
            if (!isLeapYear) {
                total--;
            }
        }
        String[] week = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        return week[(int) Math.floorMod(total - DAYS_0000_TO_1970 + 3, 7)];
    }

    public String dayOfTheWeek2(int day, int month, int year) {
        if (month == 1 || month == 2) {
            month += 12;
            year--;
        }

        int iWeek = (day + 2 * month + 3 * (month + 1) / 5 + year + year / 4 - year / 100 + year / 400) % 7;//吉姆拉尔森

        String s[] = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        return s[iWeek];
    }
}
