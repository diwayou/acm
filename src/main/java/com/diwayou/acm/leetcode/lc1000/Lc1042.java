package com.diwayou.acm.leetcode.lc1000;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/flower-planting-with-no-adjacent/
 *
 * 有N个花园，按从1到N标记。在每个花园中，你打算种下四种花之一。
 * paths[i] = [x, y]描述了花园x 到花园y的双向路径。
 * 另外，没有花园有 3 条以上的路径可以进入或者离开。
 * 你需要为每个花园选择一种花，使得通过路径相连的任何两个花园中的花的种类互不相同。
 * 以数组形式返回选择的方案作为答案answer，其中answer[i]为在第(i+1)个花园中种植的花的种类。花的种类用 1, 2, 3,4 表示。保证存在答案。
 *
 * 示例 1：
 * 输入：N = 3, paths = [[1,2],[2,3],[3,1]]
 * 输出：[1,2,3]
 *
 * 示例 2：
 * 输入：N = 4, paths = [[1,2],[3,4]]
 * 输出：[1,2,1,2]
 *
 * 示例 3：
 * 输入：N = 4, paths = [[1,2],[2,3],[3,4],[4,1],[1,3],[2,4]]
 * 输出：[1,2,3,4]
 *
 * 提示：
 * 1 <= N <= 10000
 * 0 <= paths.size <= 20000
 * 不存在花园有 4 条或者更多路径可以进入或离开。
 * 保证存在答案。
 */
public class Lc1042 {

    public static void main(String[] args) {
        int N = 4;
        int[][] paths = new int[][]{{1,2},{2,3},{3,4},{4,1},{1,3},{2,4}};

        System.out.println(Arrays.toString(new Lc1042().gardenNoAdj(N, paths)));
    }

    public int[] gardenNoAdj(int N, int[][] paths) {
        List<Integer>[] adjs = new List[N];
        for (int[] p : paths) {
            if (adjs[p[0] - 1] == null) {
                adjs[p[0] - 1] = new ArrayList<>();
            }
            if (adjs[p[1] - 1] == null) {
                adjs[p[1] - 1] = new ArrayList<>();
            }

            adjs[p[0] - 1].add(p[1] - 1);
            adjs[p[1] - 1].add(p[0] - 1);
        }

        int[] re = new int[N];
        for (int i = 0; i < N; i++) {
            List<Integer> adj = adjs[i];
            if (adj == null) {
                re[i] = 1;
            } else {
                boolean[] use = new boolean[5];
                for (int j : adj) {
                    use[re[j]] = true;
                }
                for (int c = 1; c <= 4; c++) {
                    if (!use[c]) {
                        re[i] = c;
                        break;
                    }
                }
            }
        }

        return re;
    }

    public int[] gardenNoAdj1(int N, int[][] paths) {
        // 1. 如果没有花园是相通的，那么每个花园种第一种花即可
        if (paths.length == 0) {
            int[] flowers = new int[N];
            Arrays.fill(flowers, 1);
            return flowers;
        }

        // 2. 创建长度为N+1的二维数组，每个元素是长度为5的数组。元素0记录有多少个花园相邻，元素123存放相邻的花园，元素4存放需植花的种类（用1、2、3、4表示）
        int[][] result = new int[N + 1][5];
        // 3. 遍历paths数组，每遍历一次，把相邻的两个花园记录到result数组中
        for (int[] path : paths) {
            result[path[0]][0]++; // 源花园有个宿花园
            result[path[0]][result[path[0]][0]] = path[1]; // 记录相邻的宿花园
            result[path[1]][0]++; // 宿花园有个源花园
            result[path[1]][result[path[1]][0]] = path[0]; // 记录相邻的源花园
        }

        // 4. 遍历result数组种花，每次种花都从1到4依次查询，看有没有相邻的花园已经种了
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= 4; j++) {
                int k = 0; //定义在外面，好判断进行到哪了
                for (k = 1; k < 4; k++) {//看这个花有没有相邻花园使用
                    if (result[result[i][k]][4] == j) break;//
                }
                if (k == 4) {
                    result[i][4] = j;
                    break;
                }
            }
        }
        int[] result2 = new int[N];//真正的结果
        for (int i = 0; i < N; i++) {
            result2[i] = result[i + 1][4];
        }

        return result2;
    }
}
