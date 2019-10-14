package com.diwayou.acm.leetcode.lc0;

/**
 * https://leetcode-cn.com/problems/count-and-say/
 *
 * 报数序列是一个整数序列，按照其中的整数的顺序进行报数，得到下一个数。其前五项如下：
 *
 * 1.     1
 * 2.     11
 * 3.     21
 * 4.     1211
 * 5.     111221
 * 6.     312211
 * 1 被读作  "one 1"  ("一个一") , 即 11。
 * 11 被读作 "two 1s" ("两个一"）, 即 21。
 * 21 被读作 "one 2",  "one 1" （"一个二" ,  "一个一") , 即 1211。
 *
 * 给定一个正整数 n（1 ≤ n ≤ 30），输出报数序列的第 n 项。
 *
 * 注意：整数顺序将表示为一个字符串。
 *
 * 示例 1:
 * 输入: 1
 * 输出: "1"
 *
 * 示例 2:
 * 输入: 4
 * 输出: "1211"
 */
public class Lc38 {

    public static void main(String[] args) {
        for (int i = 1; i < 10; i++) {
            System.out.println(new Lc38().countAndSay(i));
        }
    }

    public String countAndSay(int n) {
        StringBuilder re = new StringBuilder("1"), t;
        char last;
        int cnt;
        while (--n > 0) {
            t = new StringBuilder();
            cnt = 1;
            last = re.charAt(0);
            for (int i = 1; i < re.length(); i++) {
                if (re.charAt(i) != last) {
                    t.append((char)(cnt + '0'));
                    t.append(last);

                    cnt = 1;
                    last = re.charAt(i);
                } else {
                    cnt++;
                }
            }
            t.append((char)(cnt + '0'));
            t.append(last);

            re = t;
        }

        return re.toString();
    }
}
