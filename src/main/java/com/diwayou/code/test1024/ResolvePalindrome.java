package com.diwayou.code.test1024;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author gaopeng
 * @date 2020/10/12
 */
public class ResolvePalindrome {

    public static void main(String[] args) throws IOException {
        Files.lines(Path.of(GeneratePalindromeData.filename), StandardCharsets.UTF_8)
                .filter(ResolvePalindrome::isPalindrome)
                .forEach(System.out::println);
    }

    public static boolean isPalindrome(String s) {
        if (StringUtils.isBlank(s)) {
            return false;
        }

        int len = s.length();
        int halfLen = len / 2;
        for (int i = 0; i < halfLen; i++) {
            if (s.charAt(i) != s.charAt(len - i - 1)) {
                return false;
            }
        }

        return true;
    }
}
