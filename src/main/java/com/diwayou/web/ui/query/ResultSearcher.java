package com.diwayou.web.ui.query;

import com.diwayou.image.ImageUtil;
import com.diwayou.web.store.IndexFieldName;
import com.diwayou.web.store.LucenePageStoreQuery;
import com.diwayou.web.store.QueryResult;
import com.diwayou.web.store.StoreQuery;
import com.diwayou.web.ui.spider.SpiderSingleton;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;

import javax.swing.*;
import java.io.Closeable;
import java.io.IOException;

public class ResultSearcher implements Closeable {

    private ResultTableModel tableModel;

    private LucenePageStoreQuery lucenePageStoreQuery;

    private int total;

    private StoreQuery<Query> query;

    private boolean hasNextPage = false;

    public ResultSearcher(ResultTableModel tableModel) throws IOException {
        this.tableModel = tableModel;
        this.lucenePageStoreQuery = new LucenePageStoreQuery(SpiderSingleton.one().getIndexPath());
    }

    public void search(String type, String keyword) throws ParseException, IOException {
        QueryParser parser = new QueryParser("content", new SmartChineseAnalyzer());
        IndexFieldName indexFieldName = IndexFieldName.from(type);

        String queryText = indexFieldName.name() + ":" + keyword;
        Query q = parser.parse(queryText);

        query = StoreQuery.create(q);
        query.setPageNum(1)
                .setPageSize(tableModel.getRowCount());
        doQuery();
    }

    private void doQuery() throws IOException {
        QueryResult result = lucenePageStoreQuery.query(query);
        total = result.getTotal();

        if (result.getDocs().size() >= tableModel.getRowCount()) {
            hasNextPage = true;
        } else {
            hasNextPage = false;
        }

        display(result);
    }

    public void nextPage() throws IOException {
        if (!hasNextPage) {
            JOptionPane.showInternalMessageDialog(null, "已经到最后一页", "警告", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        query.setPageNum(query.getPageNum() + 1);

        doQuery();
    }

    public void prevPage() throws IOException {
        if (query.getPageNum() <= 1) {
            JOptionPane.showInternalMessageDialog(null, "已经到第一页", "警告", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        query.setPageNum(query.getPageNum() - 1);

        doQuery();
    }

    private void display(QueryResult result) {
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            if (row >= result.getDocs().size()) {
                removeRow(row);
                continue;
            }

            Document doc = result.getDocs().get(row);
            setRow(doc, row);
        }
    }

    private void setRow(Document doc, int row) {
        tableModel.setValueAt(doc.get(IndexFieldName.parentUrl.name()), row, 0);
        tableModel.setValueAt(doc.get(IndexFieldName.url.name()), row, 1);
        String type = doc.get(IndexFieldName.type.name());
        tableModel.setValueAt(type, row, 2);
        String content = doc.get(IndexFieldName.content.name());
        tableModel.setValueAt(content, row, 3);

        if (type.equalsIgnoreCase("image")) {
            ImageIcon image = new ImageIcon(content);
            tableModel.setValueAt(new ImageIcon(ImageUtil.getScaledImage(image.getImage(), 32, 32)), row, 4);
        } else {
            tableModel.setValueAt(null, row, 4);
        }
    }

    private void removeRow(int row) {
        for (int col = 0; col < tableModel.getColumnCount(); col++) {
            tableModel.setValueAt(null, row, col);
        }
    }

    @Override
    public void close() throws IOException {
        if (lucenePageStoreQuery != null) {
            lucenePageStoreQuery.close();
        }
    }
}
