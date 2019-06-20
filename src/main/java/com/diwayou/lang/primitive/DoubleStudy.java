package com.diwayou.lang.primitive;

import com.google.common.math.DoubleMath;

/**
 * https://howtodoinjava.com/java/basics/correctly-compare-float-double/
 */
public class DoubleStudy {
    public static void main(String[] args) {
        //Method 1
        double f1 = .0;
        for (int i = 1; i <= 11; i++) {
            f1 += .1;
        }

        //Method 2
        double f2 = .1 * 11;

        System.out.println("f1 = " + f1);
        System.out.println("f2 = " + f2);

        if (f1 == f2)
            System.out.println("f1 and f2 are equal\n");
        else
            System.out.println("f1 and f2 are not equal\n");

        if (DoubleMath.fuzzyEquals(f1, f2, .0001))
            System.out.println("f1 and f2 are equal using threshold\n");
        else
            System.out.println("f1 and f2 are not equal using threshold\n");
    }
}
