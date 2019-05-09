package com.diwayou.web.store;

import com.diwayou.web.domain.Page;
import com.diwayou.web.support.PageUtil;
import com.diwayou.web.url.UrlUtil;
import com.google.common.base.Preconditions;
import com.hankcs.hanlp.HanLP;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.jsoup.Jsoup;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LucenePageStore implements PageStore, Closeable {

    private static final Logger log = Logger.getLogger(LucenePageStore.class.getName());

    private FilePageStore filePageStore;

    private Path indexPath;

    private Analyzer analyzer;

    private IndexWriter indexWriter;

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public LucenePageStore(FilePageStore filePageStore, Path indexPath) throws IOException {
        Preconditions.checkNotNull(filePageStore);
        Preconditions.checkNotNull(indexPath);

        this.filePageStore = filePageStore;
        this.indexPath = indexPath;
        this.analyzer = new SmartChineseAnalyzer();

        init();
    }

    private void init() throws IOException {
        Directory dir = FSDirectory.open(indexPath);

        IndexWriterConfig iwc = new IndexWriterConfig(analyzer)
                .setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);

        indexWriter = new IndexWriter(dir, iwc);

        executorService.scheduleWithFixedDelay(() -> {
            try {
                indexWriter.commit();
            } catch (IOException e) {
                log.log(Level.WARNING, "", e);
            }
        }, 5, 3, TimeUnit.SECONDS);
    }

    @Override
    public StoreResult store(Page page, PageStoreContext context) {
        Document doc = new Document();
        doc.add(new TextField("parentUrl", UrlUtil.urlToFilename(page.getRequest().getParentUrl()), Field.Store.YES));
        doc.add(new TextField("url", UrlUtil.urlToFilename(page.getRequest().getParentUrl()), Field.Store.YES));

        if (PageUtil.isHtml(page)) {
            doc.add(new StringField("type", "html", Field.Store.NO));

            String text = Jsoup.parse(page.bodyAsString()).text();
            List<String> summary = HanLP.extractSummary(text, 2);
            doc.add(new TextField("content", StringUtils.join(summary, ","), Field.Store.YES));
        } else {
            doc.add(new StringField("type", PageUtil.getExt(page), Field.Store.NO));
            StoreResult result = filePageStore.store(page, context);
            doc.add(new TextField("content", result.getPath(), Field.Store.YES));
        }

        try {
            indexWriter.updateDocument(new Term("id", page.getRequest().getUrl()), doc);

            return new StoreResult(page.getRequest().getUrl());
        } catch (Exception e) {
            log.log(Level.WARNING, "", e);
        }

        return StoreResult.empty;
    }


    @Override
    public void close() throws IOException {
        if (this.indexWriter != null) {
            this.indexWriter.close();
        }
    }
}
