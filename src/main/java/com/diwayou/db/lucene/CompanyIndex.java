package com.diwayou.db.lucene;

import com.diwayou.db.lucene.ik.IKAnalyzer;
import com.diwayou.web.log.AppLog;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompanyIndex implements AutoCloseable {

    private static final Logger log = AppLog.getCrawl();

    private Path indexPath;

    private Analyzer analyzer;

    private IndexWriter indexWriter;

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public CompanyIndex(Path indexPath) throws IOException {
        Preconditions.checkNotNull(indexPath);

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
                log.log(Level.WARNING, "", e);
            }
        }, 5, 5, TimeUnit.SECONDS);
    }

    public void store(String name, String uuid, String regDate,
                      String type, String people,
                      String regMoney, String content, String province,
                      String city, String address) {
        Document doc = new Document();
        doc.add(new TextField("name", name, Field.Store.YES));
        doc.add(new StringField("uuid", uuid, Field.Store.YES));
        doc.add(new StringField("regDate", regDate, Field.Store.YES));
        doc.add(new StringField("regDateS", parseDate(regDate), Field.Store.YES));
        doc.add(new StringField("type", type, Field.Store.YES));
        doc.add(new StringField("people", people, Field.Store.YES));
        doc.add(new StringField("regMoney", regMoney, Field.Store.YES));
        doc.add(new LongPoint("rmLong", parseMoney(regMoney)));
        doc.add(new TextField("content", content, Field.Store.YES));
        doc.add(new StringField("province", province, Field.Store.YES));
        doc.add(new StringField("city", city, Field.Store.YES));
        doc.add(new TextField("address", address, Field.Store.YES));

        try {
            indexWriter.updateDocument(new Term("uuid", uuid), doc);

        } catch (Exception e) {
            log.log(Level.WARNING, "", e);
        }
    }

    private static long parseMoney(String regMoney) {
        regMoney = regMoney.substring(1);
        StringBuilder money = new StringBuilder();
        for (char c : regMoney.toCharArray()) {
            if (Character.isDigit(c)) {
                money.append(c);
            } else {
                break;
            }
        }

        if (money.length() == 0) {
            return 0;
        }

        return Long.parseLong(money.toString());
    }

    private static String parseDate(String regDate) {
        try {
            LocalDate date = LocalDate.parse(regDate, DateTimeFormatter.ISO_DATE);
            return date.format(DateTimeFormatter.BASIC_ISO_DATE);
        } catch (Exception e) {
            log.log(Level.INFO, regDate, e);
            return regDate;
        }
    }

    @Override
    public void close() throws IOException {
        executorService.shutdownNow();
        if (this.indexWriter != null) {
            this.indexWriter.close();
        }
    }

    public static void main(String[] args) throws IOException {
        Path indexPath = Path.of("/tmp/company");
        Path companyDataPath = Path.of("D:\\opensource\\Enterprise-Registration-Data-of-Chinese-Mainland\\Enterprise-Registration-Data\\csv");
        try (CompanyIndex index = new CompanyIndex(indexPath)) {
            Stopwatch stopwatch = Stopwatch.createStarted();
            Files.walkFileTree(companyDataPath, new FileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    log.log(Level.INFO, "preVisitDirectory" + dir.toString());
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    log.log(Level.INFO, "visitFile" + file.toString());

                    indexFile(index, file);

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    log.log(Level.WARNING, "visitFileFailed" + file.toString(), exc);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    log.log(Level.INFO, "postVisitDirectory" + dir.toString(), exc);

                    return FileVisitResult.CONTINUE;
                }
            });
            stopwatch.stop();
            System.out.println("建立索引消耗" + stopwatch.elapsed(TimeUnit.SECONDS) + "秒");
        }
    }

    private static void indexFile(CompanyIndex index, Path file) {
        try {
            Files.lines(file)
                    .skip(1)
                    //.parallel()
                    .forEach(l -> {
                        String[] cols = l.split(",");
                        index.store(cols[0], cols[1], cols[2], cols[3], cols[4], cols[5], cols[6], cols[7], cols[8], cols[9]);
                    });
        } catch (IOException e) {
            log.log(Level.WARNING, "", e);
        }
    }
}
