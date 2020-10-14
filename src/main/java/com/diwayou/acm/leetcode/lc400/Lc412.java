package com.diwayou.acm.leetcode.lc400;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/fizz-buzz/
 *
 * 写一个程序，输出从 1 到 n 数字的字符串表示。
 * 1. 如果n是3的倍数，输出“Fizz”；
 * 2. 如果n是5的倍数，输出“Buzz”；
 * 3.如果n同时是3和5的倍数，输出 “FizzBuzz”。
 *
 * 示例：
 * n = 15,
 * 返回:
 * [
 *     "1",
 *     "2",
 *     "Fizz",
 *     "4",
 *     "Buzz",
 *     "Fizz",
 *     "7",
 *     "8",
 *     "Fizz",
 *     "Buzz",
 *     "11",
 *     "Fizz",
 *     "13",
 *     "14",
 *     "FizzBuzz"
 * ]
 */
public class Lc412 {

    public List<String> fizzBuzz(int n) {
        List<String> re = new ArrayList<>(n);
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 == 0) {
                re.add("FizzBuzz");
            } else if (i % 3 == 0) {
                re.add("Fizz");
            } else if (i % 5 == 0) {
                re.add("Buzz");
            } else {
                re.add(String.valueOf(i));
            }
        }

        return re;
    }
}
