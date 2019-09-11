package com.diwayou.team;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 给定两个整数W和K，W代表你拥有的初始资金，K代表你最多可以做K个项目。再给定两个长度为N的正整数数组costs[]和profits[]，代表一共有N个项目，
 * costs[i]和profits[i]分别表示第i号项目的启动资金与做完后的利润（注意是利润，如果一个项目的启动资金是10，利润为4，代表该项目最终的收入是14）。
 * 你不能并行只能串行的做项目，并且手里拥有的资金大于或等于某个项目的启动资金时，你才能做这个项目。该如何选择做项目，能让你最终的收益最大？
 * 返回最后能够获得的最大资金。
 * <p>
 * 【举例】
 * W = 3
 * K = 2
 * costs = {5, 4, 1, 2}
 * profits = {3, 5, 3, 2}
 * 输出：11
 * <p>
 * 【时间复杂度】
 * O(KlogN)
 */
public class ProjectMaxProfit {
    public static void main(String[] args) {
        int w = 3;
        int k = 2;
        int[] costs = {5, 4, 1, 2};
        int[] profits = {3, 5, 3, 2};
        System.out.println(maxProfit(w, k, costs, profits));
    }

    public static int maxProfit(int w, int k, int[] costs, int[] profits) {
        PriorityQueue<Project> costsQueue = new PriorityQueue<>(costs.length, Comparator.comparingInt(Project::getCost));
        PriorityQueue<Project> profitsQueue = new PriorityQueue<>(Comparator.comparingInt(Project::getProfit).reversed());

        for (int i = 0; i < costs.length; i++) {
            costsQueue.offer(new Project(costs[i], profits[i]));
        }

        int maxProfit = w;
        int projectRemain = k;
        while (projectRemain > 0) {
            while (!costsQueue.isEmpty()) {
                Project minCostProject = costsQueue.peek();
                if (minCostProject != null && minCostProject.getCost() <= maxProfit) {
                    profitsQueue.offer(minCostProject);
                    costsQueue.poll();
                } else {
                    break;
                }
            }

            Project maxProfitProject = profitsQueue.poll();
            if (maxProfitProject == null) {
                break;
            }

            maxProfit += maxProfitProject.getProfit();
            projectRemain--;
        }

        return maxProfit;
    }

    private static class Project {
        public Project(int cost, int profit) {
            this.cost = cost;
            this.profit = profit;
        }

        private int cost;

        private int profit;

        public int getCost() {
            return cost;
        }

        public Project setCost(int cost) {
            this.cost = cost;
            return this;
        }

        public int getProfit() {
            return profit;
        }

        public Project setProfit(int profit) {
            this.profit = profit;
            return this;
        }
    }
}
