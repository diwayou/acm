package com.diwayou.guava.reflect;

import com.google.common.reflect.TypeToken;

import java.util.List;

public class TypeExamples {

    public static void main(String[] args) {
        TypeToken<List<String>> t = new TypeToken<List<String>>() {
        };

        System.out.println(t.toString());
        System.out.println(t.getType());
    }
}
