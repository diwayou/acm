package com.diwayou.acm.leetcode.lc1100;

/**
 * https://leetcode-cn.com/problems/defanging-an-ip-address/
 * <p>
 * 给你一个有效的 IPv4 地址address，返回这个 IP 地址的无效化版本。
 * 所谓无效化IP 地址，其实就是用"[.]"代替了每个 "."。
 * <p>
 * 示例 1：
 * 输入：address = "1.1.1.1"
 * 输出："1[.]1[.]1[.]1"
 * <p>
 * 示例 2：
 * 输入：address = "255.100.50.0"
 * 输出："255[.]100[.]50[.]0"
 * <p>
 * <p>
 * 提示：
 * 给出的address是一个有效的 IPv4 地址
 */
public class Lc1108 {

    public String defangIPaddr(String address) {
        StringBuilder re = new StringBuilder(address.length() + 6);
        for (char c : address.toCharArray()) {
            if (c == '.') {
                re.append("[.]");
            } else {
                re.append(c);
            }
        }

        return re.toString();
    }
}
