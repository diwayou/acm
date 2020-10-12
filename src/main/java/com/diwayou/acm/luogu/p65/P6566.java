package com.diwayou.acm.luogu.p65;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author gaopeng
 * @date 2020/9/30
 */
public class P6566 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int n = in.nextInt();
        int m = in.nextInt();
        in.nextLine();

        boolean[][] G = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            char[] line = in.nextLine().trim().toCharArray();
            for (int j = 0; j < m; j++) {
                if (line[j] == '*') {
                    G[i][j] = true;
                } else {
                    G[i][j] = false;
                }
            }
        }

        Map<Integer, Integer> cnt = new HashMap<>();
        int max = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (G[i][j]) {
                    int c = dfs(G, i, j);
                    Integer v = cnt.get(c);
                    int lmax = c;
                    if (v == null) {
                        cnt.put(c, lmax);
                    } else {
                        lmax = v + c;
                        cnt.put(c, lmax);
                    }

                    if (lmax > max) {
                        max = lmax;
                    }
                }
            }
        }

        System.out.printf("%d %d", cnt.size(), max);
    }

    private static int dfs(boolean[][] G, int i, int j) {
        G[i][j] = false;
        int c = 0;
        int n = G.length - 1;
        int m = G[0].length - 1;
        if (j > 0 && G[i][j - 1]) {
            c += dfs(G, i, j - 1);
        }
        if (i > 0 && j > 0 && G[i - 1][j - 1]) {
            c += dfs(G, i - 1, j - 1);
        }
        if (i > 0 && G[i - 1][j]) {
            c += dfs(G, i - 1, j);
        }
        if (i > 0 && j < m && G[i - 1][j + 1]) {
            c += dfs(G, i - 1, j + 1);
        }
        if (j < m && G[i][j + 1]) {
            c += dfs(G, i, j + 1);
        }
        if (i < n && j < m && G[i + 1][j + 1]) {
            c += dfs(G, i + 1, j + 1);
        }
        if (i < n && G[i + 1][j]) {
            c += dfs(G, i + 1, j);
        }
        if (i < n && j > 0 && G[i + 1][j - 1]) {
            c += dfs(G, i + 1, j - 1);
        }

        return 1 + c;
    }
}
