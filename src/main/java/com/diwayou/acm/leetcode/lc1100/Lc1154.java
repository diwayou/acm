package com.diwayou.acm.leetcode.lc1100;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * https://leetcode-cn.com/problems/day-of-the-year/
 * <p>
 * 给你一个按 YYYY-MM-DD 格式表示日期的字符串date，请你计算并返回该日期是当年的第几天。
 * 通常情况下，我们认为 1 月 1 日是每年的第 1 天，1 月 2 日是每年的第 2 天，依此类推。每个月的天数与现行公元纪年法（格里高利历）一致。
 * <p>
 * 示例 1：
 * 输入：date = "2019-01-09"
 * 输出：9
 * <p>
 * 示例 2：
 * 输入：date = "2019-02-10"
 * 输出：41
 * <p>
 * 示例 3：
 * 输入：date = "2003-03-01"
 * 输出：60
 * <p>
 * 示例 4：
 * 输入：date = "2004-03-01"
 * 输出：61
 * <p>
 * 提示：
 * date.length == 10
 * date[4] == date[7] == '-'，其他的date[i]都是数字。
 * date 表示的范围从 1900 年 1 月 1 日至 2019 年 12 月 31 日。
 */
public class Lc1154 {

    public static void main(String[] args) {
        System.out.println(new Lc1154().dayOfYear("2019-01-09"));
    }

    public int dayOfYear1(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ISO_DATE).getDayOfYear();
    }

    public int dayOfYear(String date) {
        int[] days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        String[] dates = date.split("-");
        if (isLeapYear(dates[0])) {
            days[1] = 29;
        }
        int sum = 0;
        int month = Integer.parseInt(dates[1]);
        for (int i = 1; i < month; i++) {
            sum += days[i - 1];
        }

        return sum + Integer.parseInt(dates[2]);
    }

    public boolean isLeapYear(String year) {
        int value = Integer.parseInt(year);
        return ((value % 4 == 0 && value % 100 != 0) || value % 400 == 0);
    }
}
