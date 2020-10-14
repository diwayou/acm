package com.diwayou.acm.leetcode.lc1200;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/find-positive-integer-solution-for-a-given-equation/
 *
 * 给出一个函数f(x, y)和一个目标结果z，请你计算方程f(x,y) == z所有可能的正整数 数对x 和 y。
 * 给定函数是严格单调的，也就是说：
 * f(x, y) < f(x + 1, y)
 * f(x, y) < f(x, y + 1)
 * 函数接口定义如下：
 * interface CustomFunction {
 * public:
 *  // Returns positive integer f(x, y) for any given positive integer x and y.
 *  int f(int x, int y);
 * };
 * 如果你想自定义测试，你可以输入整数function_id和一个目标结果z作为输入，其中function_id表示一个隐藏函数列表中的一个函数编号，题目只会告诉你列表中的 2 个函数。 
 * 你可以将满足条件的 结果数对 按任意顺序返回。
 *
 * 示例 1：
 * 输入：function_id = 1, z = 5
 * 输出：[[1,4],[2,3],[3,2],[4,1]]
 * 解释：function_id = 1 表示 f(x, y) = x + y
 * 示例 2：
 * 输入：function_id = 2, z = 5
 * 输出：[[1,5],[5,1]]
 * 解释：function_id = 2 表示 f(x, y) = x * y
 *
 * 提示：
 * 1 <= function_id <= 9
 * 1 <= z <= 100
 * 题目保证f(x, y) == z的解处于1 <= x, y <= 1000的范围内。
 * 在 1 <= x, y <= 1000的前提下，题目保证f(x, y)是一个32 位有符号整数。
 */
public class Lc1237 {

    public List<List<Integer>> findSolution(CustomFunction customfunction, int z) {
        List<List<Integer>> re = new ArrayList<>();
        for (int x = 1; x <= z; x++) {
            for (int y = 1; y <= z; y++) {
                int v = customfunction.f(x, y);
                if (v > z) {
                    break;
                } else if (v == z) {
                    re.add(Arrays.asList(x, y));
                }
            }
        }

        return re;
    }

    interface CustomFunction {
        int f(int x, int y);
    }
}
