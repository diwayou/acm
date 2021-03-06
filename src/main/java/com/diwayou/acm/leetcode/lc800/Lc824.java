package com.diwayou.acm.leetcode.lc800;

/**
 * https://leetcode-cn.com/problems/goat-latin/
 * <p>
 * 给定一个由空格分割单词的句子S。每个单词只包含大写或小写字母。
 * 我们要将句子转换为“Goat Latin”（一种类似于 猪拉丁文- Pig Latin 的虚构语言）。
 * <p>
 * 山羊拉丁文的规则如下：
 * 如果单词以元音开头（a, e, i, o, u），在单词后添加"ma"。
 * 例如，单词"apple"变为"applema"。
 * 如果单词以辅音字母开头（即非元音字母），移除第一个字符并将它放到末尾，之后再添加"ma"。
 * 例如，单词"goat"变为"oatgma"。
 * 根据单词在句子中的索引，在单词最后添加与索引相同数量的字母'a'，索引从1开始。
 * 例如，在第一个单词后添加"a"，在第二个单词后添加"aa"，以此类推。
 * 返回将S转换为山羊拉丁文后的句子。
 * <p>
 * 示例 1:
 * 输入: "I speak Goat Latin"
 * 输出: "Imaa peaksmaaa oatGmaaaa atinLmaaaaa"
 * <p>
 * 示例 2:
 * 输入: "The quick brown fox jumped over the lazy dog"
 * 输出: "heTmaa uickqmaaa rownbmaaaa oxfmaaaaa umpedjmaaaaaa overmaaaaaaa hetmaaaaaaaa azylmaaaaaaaaa ogdmaaaaaaaaaa"
 * <p>
 * 说明:
 * S中仅包含大小写字母和空格。单词间有且仅有一个空格。
 * 1 <= S.length <= 150。
 */
public class Lc824 {

    public static void main(String[] args) {
        System.out.println(new Lc824().toGoatLatin("Each word consists of lowercase and uppercase letters only"));
    }

    public String toGoatLatin(String S) {
        StringBuilder re = new StringBuilder();
        int i = 1;
        boolean newWord = true;
        char f = ' ';
        for (char c : S.toCharArray()) {
            if (c == ' ') {
                if (f != ' ') {
                    re.append(f);
                }
                re.append("ma");
                for (int j = 0; j < i; j++) {
                    re.append('a');
                }
                re.append(' ');

                newWord = true;
                i++;
            } else if (newWord) {
                newWord = false;

                if ("aeiouAEIOU".indexOf(c) >= 0) {
                    re.append(c);
                    f = ' ';
                } else {
                    f = c;
                }
            } else {
                re.append(c);
            }
        }

        if (f != ' ') {
            re.append(f);
        }
        re.append("ma");
        for (int j = 0; j < i; j++) {
            re.append('a');
        }

        return re.toString();
    }
}
