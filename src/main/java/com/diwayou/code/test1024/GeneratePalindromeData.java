package com.diwayou.code.test1024;

import com.google.common.collect.Lists;
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
                "a",
                "bc",
                "cd",
                "def"
        };
        String[] confuse = new String[]{
                "xfds",
                "yg",
                "zf"
        };

        for (int i = 0; i < seeds.length; i++) {
            seeds[i] = seeds[i] + StringUtils.reverse(seeds[i]);
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

        int times = 10;
        List<String> data = Lists.newArrayListWithCapacity(confuseByLength.length * times + seeds.length);
        for (int i = 0; i < confuseByLength.length; i++) {
            for (int j = 0; j < times; j++) {
                data.add(confuseByLength[i]);
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
