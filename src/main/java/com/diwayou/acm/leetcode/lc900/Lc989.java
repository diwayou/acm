package com.diwayou.acm.leetcode.lc900;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/add-to-array-form-of-integer/
 *
 * 对于非负整数X而言，X的数组形式是每位数字按从左到右的顺序形成的数组。例如，如果X = 1231，那么其数组形式为[1,2,3,1]。
 * 给定非负整数 X 的数组形式A，返回整数X+K的数组形式。
 *
 * 示例 1：
 * 输入：A = [1,2,0,0], K = 34
 * 输出：[1,2,3,4]
 * 解释：1200 + 34 = 1234
 *
 * 解释 2：
 * 输入：A = [2,7,4], K = 181
 * 输出：[4,5,5]
 * 解释：274 + 181 = 455
 *
 * 示例 3：
 * 输入：A = [2,1,5], K = 806
 * 输出：[1,0,2,1]
 * 解释：215 + 806 = 1021
 *
 * 示例 4：
 * 输入：A = [9,9,9,9,9,9,9,9,9,9], K = 1
 * 输出：[1,0,0,0,0,0,0,0,0,0,0]
 * 解释：9999999999 + 1 = 10000000000
 * 
 * 提示：
 * 1 <= A.length <= 10000
 * 0 <= A[i] <= 9
 * 0 <= K <= 10000
 * 如果A.length > 1，那么A[0] != 0
 */
public class Lc989 {

    public static void main(String[] args) {
        System.out.println(new Lc989().addToArrayForm(new int[]{0}, 10000));
    }

    public List<Integer> addToArrayForm(int[] A, int K) {
        int carry = K;
        for (int i = A.length - 1; i >= 0; i--) {
            int v = A[i] + carry;
            A[i] = v % 10;
            carry = v / 10;

            if (carry == 0) {
                break;
            }
        }

        List<Integer> re;
        if (carry > 0) {
            int len = (int) Math.log10(carry) + 1;// 求得K的位数
            int[] head = new int[len];
            for (int i = head.length - 1; i >= 0; i--, carry /= 10) {
                head[i] = carry % 10;
            }

            re = new ArrayList<>(len + A.length);

            for (int i = 0; i < len; i++) {
                re.add(head[i]);
            }
        } else {
            re = new ArrayList<>(A.length);
        }

        for (int v : A) {
            re.add(v);
        }

        return re;
    }
}
