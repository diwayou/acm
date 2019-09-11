package com.diwayou.team;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/smallest-sufficient-team/
 */
public class SmallestSufficientTeam {
    public static void main(String[] args) {
//        String[] req_skills = {"mmcmnwacnhhdd", "vza", "mrxyc"};
//        List<List<String>> people = Arrays.asList(Arrays.asList("mmcmnwacnhhdd"),
//                Collections.emptyList(),
//                Collections.emptyList(),
//                Arrays.asList("vza", "mrxyc"));
        String[] req_skills = {"algorithms", "math", "java", "reactjs", "csharp", "aws"};
        List<List<String>> people = Arrays.asList(Arrays.asList("algorithms", "math", "java"),
                Arrays.asList("algorithms", "math", "reactjs"),
                Arrays.asList("java", "csharp", "aws"),
                Arrays.asList("reactjs", "csharp"),
                Arrays.asList("csharp", "math"),
                Arrays.asList("aws", "java"));

        System.out.println(Arrays.toString(new SmallestSufficientTeam().smallestSufficientTeam(req_skills, people)));

    }

    public int[] smallestSufficientTeam(String[] req_skills, List<List<String>> people) {
        int[] bPeople = new int[people.size()];
        int mask;
        for (int i = 0; i < bPeople.length; i++) {
            mask = 0;
            for (String pSkill : people.get(i)) {
                mask |= (1 << indexOf(req_skills, pSkill));
            }

            bPeople[i] = mask;
        }

        int[] dp = new int[1 << req_skills.length];
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        int[][] pt = new int[1 << req_skills.length][2];

        dp[0] = 0;
        int target = (1 << req_skills.length) - 1;
        int k;
        for (int i = 0; i < bPeople.length; i++) {
            k = bPeople[i];
            if (k == 0) {
                continue;
            }

            for (int j = target; j >= 0; --j) {
                if (dp[j] + 1 < dp[j | k]) {
                    dp[j | k] = dp[j] + 1;
                    pt[j | k] = new int[]{j, i};
                }
            }
        }

        int t = target;
        List<Integer> result = new ArrayList<>();
        while (t > 0) {
            result.add(pt[t][1]);
            t = pt[t][0];
        }

        int[] re = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            re[i] = result.get(i);
        }

        return re;
    }

    private int indexOf(String[] reqSkills, String pSkill) {
        for (int i = 0; i < reqSkills.length; i++) {
            if (reqSkills[i].equals(pSkill)) {
                return i;
            }
        }

        return -1;
    }
}
