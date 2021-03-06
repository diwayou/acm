package com.diwayou.web.app;

import com.diwayou.lucene.ik.IKAnalyzer;
import com.diwayou.web.store.LucenePageStoreQuery;
import com.diwayou.web.store.QueryResult;
import com.diwayou.web.store.StoreQuery;
import com.google.common.base.Strings;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class StoreQueryApp {
    public static void main(String[] args) throws IOException {
        QueryParser parser = new QueryParser("content", new IKAnalyzer());

        int pageSize = 10;
        Scanner in = new Scanner(System.in);
        try (LucenePageStoreQuery lucenePageStoreQuery = new LucenePageStoreQuery(Path.of("dzq/index"))) {
            while (true) {
                System.out.println("查询：");
                String text = in.nextLine();
                if (Strings.isNullOrEmpty(text)) {
                    System.out.println("查询语句不能为空!");
                    continue;
                }

                int pageNum = 1;

                Query q = null;
                try {
                    q = parser.parse(text);
                } catch (ParseException e) {
                    System.out.println("语法不正确：" + e.getMessage());
                    continue;
                }

                while (true) {
                    StoreQuery<Query> query = StoreQuery.create(q);
                    query.setPageNum(pageNum)
                            .setPageSize(pageSize);
                    QueryResult result = lucenePageStoreQuery.query(query);
                    if (result.getTotal() <= 0) {
                        break;
                    }

                    print(result);

                    if (result.getDocs().size() < pageSize) {
                        break;
                    }

                    System.out.println("继续查询下一页? Y/N");
                    if (!"Y".equalsIgnoreCase(in.nextLine())) {
                        break;
                    }

                    pageNum++;
                }
            }
        }
    }

    private static void print(QueryResult result) {
        result.getDocs().forEach(System.out::println);
        System.out.println("文档总数: " + result.getTotal());
    }
}
