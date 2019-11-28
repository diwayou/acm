package com.diwayou.acm.leetcode.lc0;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/permutation-sequence/
 *
 * 给出集合 [1,2,3,…,n]，其所有元素共有 n! 种排列。
 * 按大小顺序列出所有排列情况，并一一标记，当 n = 3 时, 所有排列如下：
 * "123"
 * "132"
 * "213"
 * "231"
 * "312"
 * "321"
 * 给定 n 和 k，返回第 k 个排列。
 *
 * 说明：
 * 给定 n 的范围是 [1, 9]。
 * 给定 k 的范围是[1,  n!]。
 *
 * 示例 1:
 * 输入: n = 3, k = 3
 * 输出: "213"
 *
 * 示例 2:
 * 输入: n = 4, k = 9
 * 输出: "2314"
 */
public class Lc60 {

    public String getPermutation2(int n, int k) {
        String ans = "";
        boolean[] used = new boolean[n];
        int factor = 1;
        for (int i = 1; i < n; i++) {
            factor *= i;
        }
        k--;
        for (int i = 0; i < n; i++) {
            int currentIndex = k / factor;
            k %= factor;
            for (int j = 0; j < n; j++) {
                if (!used[j]) {
                    if (currentIndex == 0) {
                        used[j] = true;
                        ans += j + 1;
                        break;
                    } else {
                        currentIndex--;
                    }
                }
            }
            if (i < n - 1) {
                factor /= n - 1 - i;
            }
        }
        return ans;
    }

    public String getPermutation3(int n, int k) {
        k = k - 1;
        List<String> s = new ArrayList<>();
        int all = 1;
        String res = "";
        for (int i = 1; i <= n; i++) {
            s.add(String.valueOf(i));
            all = all * i;
        }
        for (int i = 0; i < n; i++) {
            all = all / (n - i);
            int index = k / all;
            res = res + s.get(index);
            s.remove(index);
            k = k - all * index;
        }

        return res;
    }

    String getPermutation = "";
    int result1 = 0;

    public String getPermutation(int n, int k) {
        if (n == 1) return "1";
        int factor[] = {1, 1, 2, 6, 24, 120, 720, 5040, 40320};
        int sv = k / factor[n - 1];
        k = k % factor[n - 1];
        sv = k > 0 ? sv + 1 : sv;
        if (k == 0) k = factor[n - 1];
        backMethod("", n, k, sv);
        return getPermutation;
    }

    public void backMethod(String val, int n, int k, int sv) {
        if (!"".equals(getPermutation)) return;
        if (val.length() == n) {
            result1++;
            if (result1 == k && "".equals(getPermutation)) {
                getPermutation = new String(val);
            }
            return;
        } else {
            for (int i = sv; i <= n; i++) {
                if (val.contains(i + "")) {
                    continue;
                }
                backMethod(val + i, n, k, 1);
            }
        }
    }

    public String getPermutation1(int n, int k) {
        StringBuilder sb = new StringBuilder();
        //每个阶层的值
        int factor[] = {1, 1, 2, 6, 24, 120, 720, 5040, 40320};
        List<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        //从第一位开始算每一位的值
        for (int i = n; i > 0; i--) {
            //这是每一位的值所在的具体位置
            /**
             * 举个例子：4，9
             * 具体第一位的排列是
             * 1xxx、2xxx、3xxx、4xxx
             * 这四种情况每一种的所得到的情况数都是3!，数目都是一样的
             * 所以我们可以用k/(n-1)!由此得知首位的值的情况
             *对应代码也就是这三行
             * int sv = k / factor[i - 1];
             * k = k % factor[i - 1];
             * sv = k > 0 ? sv + 1 : sv;如果取余大于0就对应在前面一个，和分页原理一样
             *接着我们循环计算第二的情况，前一位的余数就是第二位对应的k值
             * 以此循环得到所有的位置的值
             * */
            int sv = k / factor[i - 1];
            k = k % factor[i - 1];
            sv = k > 0 ? sv + 1 : sv;
            if (k == 0) k = factor[i - 1];
            sb.append(list.remove(sv - 1));
        }

        return sb.toString();
    }
}
