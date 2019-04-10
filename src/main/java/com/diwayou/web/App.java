package com.diwayou.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        IntStream is = IntStream.range(1, 6)
                .map(i -> i * i);

        List<Integer> result = is.map(i -> i * 2)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        System.out.println(result);
    }
}
