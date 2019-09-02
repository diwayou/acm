package com.diwayou.acm.team;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/smallest-sufficient-team/
 */
public class SmallestSufficientTeam1 {
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

        System.out.println(Arrays.toString(new SmallestSufficientTeam1().smallestSufficientTeam(req_skills, people)));

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

        Map<Integer, List<Integer>> dp = new HashMap<>();
        dp.put(0, new ArrayList<>());

        int target = (1 << req_skills.length) - 1;
        int k;
        List<Integer> dpV;
        Map<Integer, List<Integer>> tmpDp = new HashMap<>();
        for (int i = 0; i < bPeople.length; i++) {
            k = bPeople[i];
            if (k == 0) {
                continue;
            }

            tmpDp.clear();
            tmpDp.putAll(dp);
            for (Map.Entry<Integer, List<Integer>> entry : dp.entrySet()) {
                dpV = tmpDp.get(k | entry.getKey());
                if (dpV == null) {
                    dpV = new ArrayList<>(entry.getValue());
                    dpV.add(i);
                    tmpDp.put(k | entry.getKey(), dpV);
                } else if (entry.getValue().size() + 1 < dpV.size()) {
                    dpV.clear();
                    dpV.addAll(entry.getValue());
                    dpV.add(i);
                }
            }

            dp.clear();
            dp.putAll(tmpDp);
        }

        List<Integer> result = dp.get(target);

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
