package com.diwayou.web.ui.query;

import com.diwayou.db.lucene.ik.IKAnalyzer;
import com.diwayou.web.store.*;
import com.diwayou.web.ui.spider.SpiderSingleton;
import org.apache.lucene.queryparser.simple.SimpleQueryParser;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.function.BiConsumer;

public class ResultSearcher implements Closeable {

    private LucenePageStoreQuery lucenePageStoreQuery;

    private StoreQuery<Query> query;

    private int pageCount;

    private boolean hasNextPage = false;

    private BiConsumer<Page, QueryResult> displayCallback;

    private Map<String, Float> weights;

    public ResultSearcher(int pageCount, BiConsumer<Page, QueryResult> displayCallback) throws IOException {
        this.pageCount = pageCount;
        this.displayCallback = displayCallback;
        this.lucenePageStoreQuery = new LucenePageStoreQuery(SpiderSingleton.one().getIndexPath());
        this.weights = Map.of(
                IndexFieldName.ext.name(), 10000F,
                IndexFieldName.type.name(), 9000F,
                IndexFieldName.url.name(), 600F,
                IndexFieldName.parentUrl.name(), 400F,
                IndexFieldName.content.name(), 1F
        );
    }

    public QueryResult search(String keyword, int pageNum) throws IOException {
        Query q;
        if (keyword.isBlank()) {
            q = new MatchAllDocsQuery();
        } else {
            SimpleQueryParser parser = new SimpleQueryParser(new IKAnalyzer(), weights);

            q = parser.parse(keyword);
        }

        return search(q, pageNum);
    }

    public QueryResult search(Query q, int pageNum) throws IOException {
        query = StoreQuery.create(q);
        query.setPageNum(pageNum)
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
