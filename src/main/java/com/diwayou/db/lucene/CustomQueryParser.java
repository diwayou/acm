package com.diwayou.db.lucene;

import com.google.common.collect.Sets;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;

import java.util.Set;

public class CustomQueryParser extends QueryParser {

    private Set<String> pointFieldSet;

    public CustomQueryParser(String f, Analyzer a, String... pointFields) {
        super(f, a);
        this.pointFieldSet = Sets.newHashSet(pointFields);
    }

    @Override
    protected Query getRangeQuery(String field, String part1, String part2, boolean startInclusive, boolean endInclusive) throws ParseException {
        if (pointFieldSet.contains(field)) {
            return LongPoint.newRangeQuery(field, Long.parseLong(part1), Long.parseLong(part2));
        }

        return super.getRangeQuery(field, part1, part2, startInclusive, endInclusive);
    }
}
