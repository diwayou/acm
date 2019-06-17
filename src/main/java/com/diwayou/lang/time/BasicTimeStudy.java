package com.diwayou.lang.time;

import java.time.*;

public class BasicTimeStudy {
    public static void main(String[] args) {
        System.out.println(Instant.now().atZone(ZoneId.of("Asia/Shanghai")));
        System.out.println(LocalDate.now());
        System.out.println(LocalDateTime.now());
        System.out.println(LocalTime.now());
        System.out.println(MonthDay.now());
        System.out.println(OffsetDateTime.now());
        System.out.println(OffsetTime.now());
        System.out.println(Year.now());
        System.out.println(YearMonth.now());
    }
}
