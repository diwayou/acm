package com.diwayou.acm.leetcode.lc300;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://leetcode-cn.com/problems/evaluate-division/
 * <p>
 * 给出方程式A / B = k, 其中A 和B 均为代表字符串的变量，k 是一个浮点型数字。根据已知方程式求解问题，并返回计算结果。如果结果不存在，则返回-1.0。
 * <p>
 * 示例 :
 * 给定a / b = 2.0, b / c = 3.0
 * 问题: a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ?
 * 返回[6.0, 0.5, -1.0, 1.0, -1.0 ]
 * <p>
 * 输入为: vector<pair<string, string>> equations, vector<double>& values, vector<pair<string, string>>
 * queries(方程式，方程式结果，问题方程式)，其中equations.size() == values.size()，即方程式的长度与方程式结果长度相等（程式与
 * 结果一一对应），并且结果值均为正数。以上为方程式的描述。返回vector<double>类型。
 * <p>
 * 基于上述例子，输入如下：
 * equations(方程式) = [ ["a", "b"], ["b", "c"] ],
 * values(方程式结果) = [2.0, 3.0],
 * queries(问题方程式) = [ ["a", "c"], ["b", "a"], ["a", "e"], ["a", "a"], ["x", "x"] ].
 * 输入总是有效的。你可以假设除法运算中不会出现除数为0的情况，且不存在任何矛盾的结果。
 */
public class Lc399 {

    /**
     * Java 并查集解法：
     * 这里是一个特殊的并查集
     * 首先每个点对应的value是这个点和他存储的parent对应的倍数关系。
     * 比如
     * a/b = 5 b.value=5
     * c/d=3 c.value=3
     * <p>
     * b/d=2 这时候不会去更新d的数值，而是会去找d的parent c b的parent a 让这两个parent连接起来
     * <p>
     * 相当于图的连接
     * <p>
     * 那么就要求 a和c的关系
     * 这里就涉及数学推导
     * <p>
     * a/b=v1//value.get(parent)
     * c/d=v2//value.get(child)
     * <p>
     * b/d=v3//value[i]
     * <p>
     * a=(v1b)=v1v3d
     * c=v2d
     * a/c=(v1*v3)/v2
     * 所以 推导关系 出来了：
     * <p>
     * value.put(c,value[i]*value.get(parent)/value.get(child))
     */
    //child parent
    Map<String, String> parents = new HashMap<>();
    //child mutiply of parent
    Map<String, Double> values = new HashMap<>();

    public void add(String x) {
        if (!parents.containsKey(x)) {
            parents.put(x, x);
            values.put(x, 1.0);
        }
    }

    public void union(String parent, String child, double value) {
        add(parent);
        add(child);
        String p1 = find(parent);
        String p2 = find(child);
        if (p1.equals(p2)) {
            return;
        }

        parents.put(p2, p1);
        values.put(p2, value * (values.get(parent) / values.get(child)));
    }

    public String find(String x) {
        while (!parents.get(x).equals(x)) {
            x = parents.get(x);
        }

        return x;
    }


    public double cal(String x) {
        // System.out.println("cal x"+x);
        double v = values.get(x);
        while (!parents.get(x).equals(x)) {
            x = parents.get(x);
            v *= values.get(x);
        }

        return v;
    }

    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        //union
        for (int i = 0; i < equations.size(); i++) {
            //union parent child value
            union(equations.get(i).get(0), equations.get(i).get(1), values[i]);
        }

        //find
        double[] ans = new double[queries.size()];

        for (int i = 0; i < queries.size(); i++) {

            String c1 = queries.get(i).get(0);
            String c2 = queries.get(i).get(1);
            if (!parents.containsKey(c1) || !parents.containsKey(c2)) {
                ans[i] = -1;
                continue;
            }
            if (c1.equals(c2)) {
                ans[i] = 1;
                continue;
            }
            String p1 = find(c1);
            String p2 = find(c2);
            if (!p1.equals(p2)) {
                ans[i] = -1;
                continue;
            }
            ans[i] = cal(c2) / cal(c1);

        }

        return ans;


    }
}
