package com.diwayou.acm.guava.range;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;

import java.util.Date;
import java.util.List;

public class RangeExample {
    public static void main(String[] args) {
        RangeSet<Date> rangeSet = TreeRangeSet.create();

        List<Range<Date>> ranges = List.of(
                Range.closed(new Date(1000), new Date(2000)),
                Range.closed(new Date(3000), new Date(4000)),
                Range.closed(new Date(1500), new Date(2000)),
                Range.closed(new Date(3000), new Date(5000))
        );

        for (Range<Date> range : ranges) {
            System.out.println(range);
            boolean intersect = rangeSet.intersects(range);

            if (intersect) {
                System.out.println("intersect " + range);
            } else {
                rangeSet.add(range);
            }
        }
    }
}
