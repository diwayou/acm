package com.diwayou.lang.stream;

import java.util.Arrays;
import java.util.stream.LongStream;

public class LongStreamStudy {
    public static void main(String[] args) {
        long[] arr = new long[]{1, 2, 3, 4, 5};

        long[] result = LongStream.of(arr)
                .map(i -> i * 2)
                .filter(i -> i > 5)
                .toArray();

        System.out.println(Arrays.toString(result));
    }
}
