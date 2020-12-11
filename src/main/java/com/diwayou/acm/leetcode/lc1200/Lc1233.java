package com.diwayou.acm.leetcode.lc1200;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/remove-sub-folders-from-the-filesystem/
 * <p>
 * 你是一位系统管理员，手里有一份文件夹列表 folder，你的任务是要删除该列表中的所有 子文件夹，并以 任意顺序 返回剩下的文件夹。
 * 我们这样定义「子文件夹」：
 * 如果文件夹folder[i]位于另一个文件夹folder[j]下，那么folder[i]就是folder[j]的子文件夹。
 * 文件夹的「路径」是由一个或多个按以下格式串联形成的字符串：
 * /后跟一个或者多个小写英文字母。
 * 例如，/leetcode和/leetcode/problems都是有效的路径，而空字符串和/不是。
 * <p>
 * 示例 1：
 * 输入：folder = ["/a","/a/b","/c/d","/c/d/e","/c/f"]
 * 输出：["/a","/c/d","/c/f"]
 * 解释："/a/b/" 是 "/a" 的子文件夹，而 "/c/d/e" 是 "/c/d" 的子文件夹。
 * <p>
 * 示例 2：
 * 输入：folder = ["/a","/a/b/c","/a/b/d"]
 * 输出：["/a"]
 * 解释：文件夹 "/a/b/c" 和 "/a/b/d/" 都会被删除，因为它们都是 "/a" 的子文件夹。
 * <p>
 * 示例 3：
 * 输入：folder = ["/a/b/c","/a/b/d","/a/b/ca"]
 * 输出：["/a/b/c","/a/b/ca","/a/b/d"]
 * <p>
 * 提示：
 * 1 <= folder.length<= 4 * 10^4
 * 2 <= folder[i].length <= 100
 * folder[i]只包含小写字母和 /
 * folder[i]总是以字符 /起始
 * 每个文件夹名都是唯一的
 */
public class Lc1233 {

    public static void main(String[] args) {
        System.out.println(new Lc1233().removeSubfolders(new String[]{"/a/b/c", "/a/b/ca", "/a/b/d"}));
    }

    public List<String> removeSubfolders(String[] folder) {
        Arrays.sort(folder);

        List<String> re = new ArrayList<>();
        String p = folder[0];
        re.add(p);
        for (int i = 1; i < folder.length; i++) {
            if (!(folder[i].startsWith(p) && folder[i].charAt(p.length()) == '/')) {
                p = folder[i];
                re.add(p);
            }
        }

        return re;
    }

    public List<String> removeSubfolders1(String[] folder) {
        HashSet<String> set = new HashSet<>();
        for (String str : folder) {
            set.add(str);
        }

        List<String> re = new ArrayList<>(15);
        for (String str : folder) {
            int idx = str.indexOf('/', 1);
            boolean found = false;
            while (idx != -1) {
                if (set.contains(str.substring(0, idx))) {
                    found = true;
                    break;
                }

                idx = str.indexOf('/', idx + 1);
            }

            if (!found) {
                re.add(str);
            }
        }

        return re;
    }
}
