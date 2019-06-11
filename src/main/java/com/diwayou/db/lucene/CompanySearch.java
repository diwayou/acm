package com.diwayou.db.lucene;

import com.diwayou.db.lucene.ik.IKAnalyzer;
import com.diwayou.web.store.QueryResult;
import com.diwayou.web.store.StoreQuery;
import com.google.common.collect.Lists;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.simple.SimpleQueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CompanySearch implements Closeable {

    private static final int QUERY_LIMIT = 10000;

    private IndexSearcher indexSearcher;

    private IndexReader indexReader;

    private Path indexPath;

    private int queryLimit;

    public CompanySearch(Path indexPath) throws IOException {
        this(indexPath, QUERY_LIMIT);
    }

    public CompanySearch(Path indexPath, int queryLimit) throws IOException {
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

    public static void main(String[] args) throws IOException {
        Path indexPath = Path.of("/tmp/company");
        Map<String, Float> weights = Map.of(
                "name", 10000F,
                "uuid", 9000F,
                "regDate", 600F,
                "type", Float.MAX_VALUE / 10,
                "people", Float.MAX_VALUE,
                "regMoney", 1F,
                "content", 1F,
                "province", Float.MAX_VALUE,
                "city", Float.MAX_VALUE,
                "address", Float.MAX_VALUE / 100
        );

        try (CompanySearch search = new CompanySearch(indexPath); Scanner in = new Scanner(System.in)) {
            do {
                String keyword = in.nextLine();
                if (keyword.equalsIgnoreCase("quit")) {
                    break;
                }

                Query q;
                if (keyword.isBlank()) {
                    q = new MatchAllDocsQuery();
                } else {
                    SimpleQueryParser parser = new SimpleQueryParser(new IKAnalyzer(), weights);

                    q = parser.parse(keyword);
                }

                QueryResult result = search.query(new StoreQuery<>(q));

                result.getDocs().forEach(CompanySearch::print);
            } while (true);
        }
    }

    private static void print(Document d) {
        System.out.println(String.format("%s, %s, %s, %s, %s, %s, %s, %s, %s, %s",
                d.get("name"),
                d.get("uuid"),
                d.get("regDate"),
                d.get("type"),
                d.get("people"),
                d.get("regMoney"),
                d.get("content"),
                d.get("province"),
                d.get("city"),
                d.get("address")
                ));
    }
}
