package com.diwayou.code.test1024;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author gaopeng
 * @date 2020/10/12
 */
public class GeneratePalindromeData {

    public static final String filename = "palindrome.txt";

    public static void main(String[] args) throws IOException {
        String[] seeds = new String[]{
                "男歌手",
                "嘿，饲养员，由于你不小心的bug,你养的猪从笼子跑出去了，现在你要负责往猪圈里哄它们。",
                "已婚，宇宙无敌超级帅"
        };
        String[] confuse = new String[]{
                "是时候表演真正的技术了",
                "谢谢惠顾，请您下次光临",
                "你正在进行的工作，可以简单的理解为一种计算机和人都能识别的语言"
        };

        for (int i = 0; i < seeds.length; i++) {
            seeds[i] = seeds[i] + ";" + StringUtils.reverse(seeds[i]);
        }

        String[] confuseByLength = new String[seeds.length * confuse.length];
        for (int i = 0; i < seeds.length; i++) {
            for (int j = 0; j < confuse.length; j++) {
                if (seeds[i].length() < confuse[j].length()) {
                    confuseByLength[i * confuse.length + j] = StringUtils.truncate(confuse[j], seeds[i].length());
                } else {
                    confuseByLength[i * confuse.length + j] = StringUtils.rightPad(confuse[j], seeds[i].length(), confuse[j]);
                }
            }
        }

        System.out.println(Arrays.toString(seeds));
        System.out.println(Arrays.toString(confuseByLength));

        int times = 100;
        List<String> data = Lists.newArrayListWithCapacity(confuseByLength.length * times + seeds.length);
        for (int i = 0; i < confuseByLength.length; i++) {
            for (int j = 0; j < times; j++) {
                char[] arr = confuseByLength[i].toCharArray();
                ArrayUtils.shuffle(arr);
                String s = new String(arr);
                if (ResolvePalindrome.isPalindrome(s)) {
                    s = confuseByLength[i];
                }

                data.add(s);
            }
        }
        for (int i = 0; i < seeds.length; i++) {
            data.add(seeds[i]);
        }

        Collections.shuffle(data);

        Files.write(Path.of(filename), data);

        data.stream().filter(ResolvePalindrome::isPalindrome).forEach(System.out::println);
    }
}
