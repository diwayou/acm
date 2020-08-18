package com.diwayou.lucene;

import com.diwayou.lucene.ik.IKAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

public class ChineseTest {
    public static void main(String[] args) throws IOException, ParseException {
        String indexPath = "D:/tmp/index";
        Directory dir = FSDirectory.open(Paths.get(indexPath));

        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

        try (IndexWriter writer = new IndexWriter(dir, iwc)) {
            indexDoc(writer, "1", "我是中国人!I'm Chinese!");
            indexDoc(writer, "2", "中国很大");
            indexDoc(writer, "3", "http://www.baidu.com/a/b/c?a=1&b=2");
        }

        try (IndexReader reader = DirectoryReader.open(dir)) {
            IndexSearcher searcher = new IndexSearcher(reader);
            String field = "content";
            QueryParser parser = new QueryParser(field, analyzer);

            Query query = parser.parse("content:baidu");
            System.out.println("Searching for: " + query.toString(field));

            TopScoreDocCollector collector = TopScoreDocCollector.create(2, 2);
            searcher.search(query, collector);

            int pageSize = 1;
            TopDocs results = collector.topDocs(0, pageSize);
            ScoreDoc[] hits = results.scoreDocs;

            int numTotalHits = Math.toIntExact(results.totalHits.value);
            System.out.println(numTotalHits + " total matching documents");

            for (int i = 0; i < hits.length; i++) {
                Document doc = searcher.doc(hits[i].doc);

                System.out.println(doc.get("id"));
                System.out.println(doc.get("content"));
            }
        }
    }

    private static void indexDoc(IndexWriter writer, String docId, String content) throws IOException {
        Document doc = new Document();
        doc.add(new StringField("id", docId, Field.Store.YES));
        doc.add(new TextField("content", content, Field.Store.YES));
        writer.updateDocument(new Term("id", docId), doc);
    }
}
