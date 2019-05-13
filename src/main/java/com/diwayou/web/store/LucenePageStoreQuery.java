package com.diwayou.web.store;

import com.google.common.collect.Lists;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class LucenePageStoreQuery implements Closeable {

    private static final int QUERY_LIMIT = 1000;

    private IndexSearcher indexSearcher;

    private IndexReader indexReader;

    private Path indexPath;

    private int queryLimit;

    public LucenePageStoreQuery(Path indexPath) throws IOException {
        this(indexPath, QUERY_LIMIT);
    }

    public LucenePageStoreQuery(Path indexPath, int queryLimit) throws IOException {
        this.indexPath = indexPath;
        this.queryLimit = queryLimit;

        Directory dir = FSDirectory.open(indexPath);
        indexReader = DirectoryReader.open(dir);
        indexSearcher = new IndexSearcher(indexReader);
    }

    public QueryResult query(StoreQuery<Query> storeQuery) throws IOException {
        TopScoreDocCollector collector = TopScoreDocCollector.create(QUERY_LIMIT, QUERY_LIMIT);
        indexSearcher.search(storeQuery.getQuery(), collector);
        TopDocs results = collector.topDocs(storeQuery.getStartOffset(), storeQuery.getPageSize());

        ScoreDoc[] hits = results.scoreDocs;
        int numTotalHits = (int) results.totalHits.value;

        List<Document> docs = Lists.newArrayListWithCapacity(storeQuery.getPageSize());
        for (int i = 0; i < hits.length; i++) {
            Document doc = indexSearcher.doc(hits[i].doc);

            docs.add(doc);
        }

        return new QueryResult(docs)
                .setTotal(numTotalHits);
    }

    @Override
    public void close() throws IOException {
        if (this.indexReader != null) {
            this.indexReader.close();
        }
    }
}
