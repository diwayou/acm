package com.diwayou.lang.random;

import java.util.Random;

public class RandUtil {
    public static void main(String[] args) {
        final double min = 3, max = 20;

        Random random = new Random(System.currentTimeMillis());
        int[] stat = new int[(int) max];
        for (int i = 0; i < 1000; i++) {
            double r = random(random, 1.9, 11, min, max);
            stat[(int) r] += 1;
        }

        for (int i = 0; i < stat.length; i++) {
            int v = stat[i];
            if (v != 0) {
                System.out.println(i + " -> " + v);
            }
        }
    }

    public static double random(Random random, double rPivot, double min, double max) {
        return random(random, 2, rPivot, min, max);
    }

    /**
     * 利用高斯随机算法和普通随机算法生成一个某个值以下概率更大的随机值，用了两次随机函数，没有考虑性能问题，呵呵
     *
     * @param random 随机数生成器
     * @param pivot  高斯概率分布的分界点，2左右都比较好用，根据业务场景调试，该参数越大，生成[rPivot, max]之间的数字概率越低
     * @param rPivot 希望生成[min, rPivot]的数字概率更大
     * @param min    随机数起始数值
     * @param max    随机数最大数值
     * @return 返回一个[min, max]之间的随机数，但是[min, rPivot]的概率更大
     */
    public static double random(Random random, double pivot, double rPivot, double min, double max) {
        if (rPivot <= min || rPivot >= max) {
            throw new IllegalArgumentException("rPivot must between min and max.");
        }

        double r = Math.abs(random.nextGaussian());

        double result;
        if (r < pivot) {
            result = min + Math.random() * (rPivot - min + 1);
        } else {
            result = rPivot + Math.random() * (max - rPivot);
        }

        return Math.max(min, Math.min(result, max));
    }
}
