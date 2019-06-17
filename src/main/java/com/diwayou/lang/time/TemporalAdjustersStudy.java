package com.diwayou.lang.time;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class TemporalAdjustersStudy {
    public static void main(String[] args) {
        LocalDate now = LocalDate.now();
        System.out.println(now.with(TemporalAdjusters.firstDayOfYear()));
        System.out.println(now.with(TemporalAdjusters.firstDayOfNextYear()));
        System.out.println(now.with(TemporalAdjusters.firstDayOfMonth()));
        System.out.println(now.with(TemporalAdjusters.firstDayOfNextMonth()));
        System.out.println(now.with(TemporalAdjusters.lastDayOfYear()));
        System.out.println(now.with(TemporalAdjusters.lastDayOfMonth()));
    }
}
