package com.diwayou.web.ui.query;

import com.diwayou.db.lucene.ik.IKAnalyzer;
import com.diwayou.web.store.*;
import com.diwayou.web.ui.spider.SpiderSingleton;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;

import java.io.Closeable;
import java.io.IOException;
import java.util.function.BiConsumer;

public class ResultSearcher implements Closeable {

    private LucenePageStoreQuery lucenePageStoreQuery;

    private StoreQuery<Query> query;

    private int pageCount;

    private boolean hasNextPage = false;

    private BiConsumer<Page, QueryResult> displayCallback;

    public ResultSearcher(int pageCount, BiConsumer<Page, QueryResult> displayCallback) throws IOException {
        this.pageCount = pageCount;
        this.displayCallback = displayCallback;
        this.lucenePageStoreQuery = new LucenePageStoreQuery(SpiderSingleton.one().getIndexPath());
    }

    public QueryResult search(String type, String keyword) throws ParseException, IOException {
        Query q;
        if (keyword.isBlank()) {
            q = new MatchAllDocsQuery();
        } else {
            QueryParser parser = new QueryParser("content", new IKAnalyzer());
            IndexFieldName indexFieldName = IndexFieldName.from(type);

            String queryText = indexFieldName.name() + ":" + keyword;

            // 输入构造查询语法
            if (keyword.startsWith("!")) {
                queryText = keyword.substring(1);
            }

            q = parser.parse(queryText);
        }

        query = StoreQuery.create(q);
        query.setPageNum(1)
                .setPageSize(pageCount);
        return doQuery();
    }

    private QueryResult doQuery() throws IOException {
        QueryResult result = lucenePageStoreQuery.query(query);

        if (result.getDocs().size() >= pageCount) {
            hasNextPage = true;
        } else {
            hasNextPage = false;
        }

        displayCallback.accept(query, result);

        return result;
    }

    public void nextPage() throws IOException {
        if (!hasNextPage) {
            return;
        }

        query.setPageNum(query.getPageNum() + 1);

        doQuery();
    }

    public void prevPage() throws IOException {
        if (query.getPageNum() <= 1) {
            return;
        }

        query.setPageNum(query.getPageNum() - 1);

        doQuery();
    }

    @Override
    public void close() throws IOException {
        if (lucenePageStoreQuery != null) {
            lucenePageStoreQuery.close();
        }
    }
}
