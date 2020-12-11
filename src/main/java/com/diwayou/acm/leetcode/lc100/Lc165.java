package com.diwayou.acm.leetcode.lc100;

/**
 * https://leetcode-cn.com/problems/compare-version-numbers/
 * <p>
 * 比较两个版本号 version1和 version2。
 * 如果version1>version2返回1，如果version1<version2 返回 -1， 除此之外返回 0。
 * 你可以假设版本字符串非空，并且只包含数字和. 字符。
 * . 字符不代表小数点，而是用于分隔数字序列。
 * 例如，2.5 不是“两个半”，也不是“差一半到三”，而是第二版中的第五个小版本。
 * 你可以假设版本号的每一级的默认修订版号为 0。例如，版本号 3.4 的第一级（大版本）和第二级（小版本）修订号分别为 3 和 4。其第三级和第四级修订号均为 0。
 * <p>
 * 示例1:
 * 输入: version1 = "0.1", version2 = "1.1"
 * 输出: -1
 * <p>
 * 示例 2:
 * 输入: version1 = "1.0.1", version2 = "1"
 * 输出: 1
 * <p>
 * 示例 3:
 * 输入: version1 = "7.5.2.4", version2 = "7.5.3"
 * 输出: -1
 * <p>
 * 示例4：
 * 输入：version1 = "1.01", version2 = "1.001"
 * 输出：0
 * 解释：忽略前导零，“01” 和 “001” 表示相同的数字 “1”。
 * <p>
 * 示例 5：
 * 输入：version1 = "1.0", version2 = "1.0.0"
 * 输出：0
 * 解释：version1 没有第三级修订号，这意味着它的第三级修订号默认为 “0”。
 * <p>
 * 提示：
 * 版本字符串由以点（.）分隔的数字字符串组成。这个数字字符串可能有前导零。
 * 版本字符串不以点开始或结束，并且其中不会有两个连续的点。
 */
public class Lc165 {

    public static void main(String[] args) {
        System.out.println(new Lc165().compareVersion("0.1", "1.1"));
        System.out.println(new Lc165().compareVersion("1.0.1", "1"));
        System.out.println(new Lc165().compareVersion("7.5.2.4", "7.5.3"));
        System.out.println(new Lc165().compareVersion("1.01", "1.001"));
        System.out.println(new Lc165().compareVersion("1.0", "1.0.0"));
    }

    public int compareVersion(String version1, String version2) {
        String[] v1 = version1.split("\\.");
        String[] v2 = version2.split("\\.");

        for (int i = 0; i < 4; i++) {
            int a = i < v1.length ? Integer.parseInt(v1[i]) : 0;
            int b = i < v2.length ? Integer.parseInt(v2[i]) : 0;

            if (a > b) {
                return 1;
            } else if (a < b) {
                return -1;
            }
        }

        return 0;
    }

    public int compareVersion1(String version1, String version2) {
        int i = 0, j = 0;
        while (i < version1.length() || j < version2.length()) {
            int v1 = 0, v2 = 0;
            while (i < version1.length() && version1.charAt(i) != '.') {
                v1 = v1 * 10 + version1.charAt(i++) - '0';
            }
            while (j < version2.length() && version2.charAt(j) != '.') {
                v2 = v2 * 10 + version2.charAt(j++) - '0';
            }

            if (v1 > v2) {
                return 1;
            }

            if (v1 < v2) {
                return -1;
            }

            i++;
            j++;
        }

        return 0;
    }
}
