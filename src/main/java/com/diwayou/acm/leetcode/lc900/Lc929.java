package com.diwayou.acm.leetcode.lc900;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * https://leetcode-cn.com/problems/unique-email-addresses/
 * <p>
 * 每封电子邮件都由一个本地名称和一个域名组成，以 @ 符号分隔。
 * 例如，在alice@leetcode.com中，alice是本地名称，而leetcode.com是域名。
 * 除了小写字母，这些电子邮件还可能包含 '.' 或 '+'。
 * 如果在电子邮件地址的本地名称部分中的某些字符之间添加句点（'.'），则发往那里的邮件将会转发到本地名称中没有点的同一地址。
 * 例如，"alice.z@leetcode.com” 和 “alicez@leetcode.com”会转发到同一电子邮件地址。 （请注意，此规则不适用于域名。）
 * 如果在本地名称中添加加号（'+'），则会忽略第一个加号后面的所有内容。这允许过滤某些电子邮件，例如 m.y+name@email.com 将转发到
 * my@email.com。 （同样，此规则不适用于域名。）
 * 可以同时使用这两个规则。
 * 给定电子邮件列表 emails，我们会向列表中的每个地址发送一封电子邮件。实际收到邮件的不同地址有多少？
 * <p>
 * 示例：
 * 输入：["test.email+alex@leetcode.com","test.e.mail+bob.cathy@leetcode.com","testemail+david@lee.tcode.com"]
 * 输出：2
 * 解释：实际收到邮件的是 "testemail@leetcode.com" 和 "testemail@lee.tcode.com"。
 * <p>
 * 提示：
 * 1 <= emails[i].length<= 100
 * 1 <= emails.length <= 100
 * 每封 emails[i] 都包含有且仅有一个 '@' 字符。
 */
public class Lc929 {

    public static void main(String[] args) {
        System.out.println(new Lc929().numUniqueEmails(new String[]{"test.email+alex@leetcode.com", "test.e.mail+bob.cathy@leetcode.com", "testemail+david@lee.tcode.com"}));
    }

    public int numUniqueEmails1(String[] emails) {
        Map<String, Set<String>> map = new HashMap<>();

        for (String e : emails) {
            String[] sa = e.split("@");

            String s = sa[0];
            int p = s.indexOf('+');
            if (p >= 0) {
                s = s.substring(0, p);
            }

            s = s.replaceAll("\\.", "");

            Set<String> set = map.get(sa[1]);
            if (set == null) {
                set = new HashSet<>();
                map.put(sa[1], set);
            }
            set.add(s);
        }

        int re = 0;
        for (Set<String> set : map.values()) {
            re += set.size();
        }

        return re;
    }

    public int numUniqueEmails(String[] emails) {
        Set<String> mails = new HashSet<String>();
        for (String email : emails) {
            mails.add(normalize(email));
        }
        return mails.size();
    }

    private String normalize(String email) {
        StringBuilder mail = new StringBuilder(email.length());

        boolean filtered = false;
        int i = 0;
        for (; i < email.length(); i++) {
            char c = email.charAt(i);
            if (c == '@')
                break;
            if (c == '.')
                continue;//skip this char
            if (c == '+')
                filtered = true;
            if (!filtered) {
                mail.append(c);
            }
        }
        mail.append(email.substring(i));

        return mail.toString();
    }
}
