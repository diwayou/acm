package com.diwayou.web.store;

import com.diwayou.lucene.ik.IKAnalyzer;
import com.diwayou.web.domain.Page;
import com.diwayou.web.support.PageUtil;
import com.diwayou.web.support.soup.ElementUtil;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class LucenePageStore implements PageStore, Closeable {

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
        this.analyzer = new IKAnalyzer();

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
                log.warn("", e);
            }
        }, 5, 5, TimeUnit.SECONDS);
    }

    @Override
    public StoreResult store(Page page) {
        Document doc = new Document();
        doc.add(new TextField(IndexFieldName.parentUrl.name(), page.getRequest().getParentUrl(), Field.Store.YES));
        doc.add(new TextField(IndexFieldName.url.name(), page.getRequest().getUrl(), Field.Store.YES));
        doc.add(new StringField("id", page.getRequest().getUrl(), Field.Store.YES));

        if (PageUtil.isHtml(page)) {
            doc.add(new StringField(IndexFieldName.type.name(), IndexType.html.name(), Field.Store.YES));
            doc.add(new StringField(IndexFieldName.ext.name(), IndexType.html.name(), Field.Store.YES));

            String text = ElementUtil.text(Jsoup.parse(page.bodyAsString()));
            doc.add(new TextField(IndexFieldName.content.name(), text, Field.Store.YES));
        } else if (PageUtil.isImage(page)) {
            doc.add(new StringField(IndexFieldName.type.name(), IndexType.image.name(), Field.Store.YES));
            doc.add(new StringField(IndexFieldName.ext.name(), PageUtil.getExt(page), Field.Store.YES));

            StoreResult result = filePageStore.store(page);
            doc.add(new TextField(IndexFieldName.content.name(), result.getPath(), Field.Store.YES));
        } else {
            doc.add(new StringField(IndexFieldName.type.name(), IndexType.doc.name(), Field.Store.YES));
            doc.add(new StringField(IndexFieldName.ext.name(), PageUtil.getExt(page), Field.Store.YES));

            StoreResult result = filePageStore.store(page);
            doc.add(new TextField(IndexFieldName.content.name(), result.getPath(), Field.Store.YES));
        }

        try {
            indexWriter.updateDocument(new Term("id", page.getRequest().getUrl()), doc);

            return new StoreResult(page.getRequest().getUrl());
        } catch (Exception e) {
            log.warn("", e);
        }

        return StoreResult.empty;
    }


    @Override
    public void close() throws IOException {
        if (this.indexWriter != null) {
            this.indexWriter.close();
        }
    }

    public Path getIndexPath() {
        return indexPath;
    }
}
