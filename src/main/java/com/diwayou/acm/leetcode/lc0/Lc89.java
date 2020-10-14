package com.diwayou.acm.leetcode.lc0;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/gray-code/
 *
 * 格雷编码是一个二进制数字系统，在该系统中，两个连续的数值仅有一个位数的差异。
 * 给定一个代表编码总位数的非负整数 n，打印其格雷编码序列。格雷编码序列必须以 0 开头。
 *
 * 示例 1:
 * 输入:2
 * 输出:[0,1,3,2]
 * 解释:
 * 00 - 0
 * 01 - 1
 * 11 - 3
 * 10 - 2
 * 对于给定的n，其格雷编码序列并不唯一。
 * 例如，[0,2,3,1]也是一个有效的格雷编码序列。
 * 00 - 0
 * 10 - 2
 * 11 - 3
 * 01 - 1
 *
 * 示例2:
 * 输入:0
 * 输出:[0]
 * 解释: 我们定义格雷编码序列必须以 0 开头。
 *     给定编码总位数为 n 的格雷编码序列，其长度为 2^n。当 n = 0 时，长度为 2^0 = 1。
 *     因此，当 n = 0 时，其格雷编码序列为 [0]。
 */
public class Lc89 {

    // https://leetcode.wang/ 发现这个网址讲解的一些东西不错 记录下
    public static void main(String[] args) {
        System.out.println(new Lc89().grayCode(2));
    }

    public List<Integer> grayCode(int n) {
        List<Integer> gray = new ArrayList<Integer>();
        gray.add(0); //初始化 n = 0 的解
        for (int i = 0; i < n; i++) {
            int add = 1 << i; //要加的数
            //倒序遍历，并且加上一个值添加到结果中
            for (int j = gray.size() - 1; j >= 0; j--) {
                gray.add(gray.get(j) + add);
            }
        }
        return gray;
    }

    public List<Integer> grayCode2(int n) {
        List<Integer> gray = new ArrayList<Integer>();
        gray.add(0); //初始化第零项
        for (int i = 1; i < 1 << n; i++) {
            //得到上一个的值
            int previous = gray.get(i - 1);
            //同第一项的情况
            if (i % 2 == 1) {
                previous ^= 1; //和 0000001 做异或，使得最右边一位取反
                gray.add(previous);
                //同第二项的情况
            } else {
                int temp = previous;
                //寻找右边起第第一个为 1 的位元
                for (int j = 0; j < n; j++) {
                    if ((temp & 1) == 1) {
                        //和 00001000000 类似这样的数做异或，使得相应位取反
                        previous = previous ^ (1 << (j + 1));
                        gray.add(previous);
                        break;
                    }
                    temp = temp >> 1;
                }
            }
        }
        return gray;
    }

    public List<Integer> grayCode1(int n) {
        int len = 1 << n;
        List<Integer> gray = new ArrayList<>(len);
        for (int i = 0; i < len; i++) {
            gray.add(i ^ (i >> 1));
        }

        return gray;
    }
}
