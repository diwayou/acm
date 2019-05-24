package com.diwayou.web.ui.query;

import com.diwayou.web.config.AppConfig;
import com.diwayou.web.log.AppLog;
import com.diwayou.web.store.IndexFieldName;
import com.diwayou.web.store.IndexType;
import com.diwayou.web.store.QueryResult;
import com.diwayou.web.ui.component.ImageFrame;
import com.diwayou.web.ui.component.TextFrame;
import com.diwayou.web.ui.swing.RobotMainFrame;
import com.diwayou.web.ui.swing.ToolPanel;
import org.apache.lucene.document.Document;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QueryFrame extends JFrame {
    private static final Logger log = AppLog.getBrowser();

    private JTable table;

    private ResultTableModel tableModel;

    private ResultSearcher searcher;

    private JComboBox<String> typeCombo;

    private JLabel searchTotalLabel;

    private RobotMainFrame mainFrame;

    private ToolPanel toolPanel;

    public QueryFrame(RobotMainFrame mainFrame, ToolPanel toolPanel) {
        this.mainFrame = mainFrame;
        this.toolPanel = toolPanel;
        this.tableModel = new ResultTableModel(20);
        this.table = new JTable(tableModel);
        initTable();

        try {
            this.searcher = new ResultSearcher(this.tableModel.getRowCount(), (q, result) -> {
                updateTotal(result.getTotal(), q.getPageNum());
                display(result);
            });
        } catch (IOException e) {
            log.log(Level.WARNING, "", e);

            JOptionPane.showInternalMessageDialog(null, "初始化查询失败e=" + e.getMessage(), "警告", JOptionPane.INFORMATION_MESSAGE);
            SwingUtilities.invokeLater(this::dispose);
        }

        final JScrollPane tableScrollPane = new JScrollPane(this.table);
        this.setLayout(new BorderLayout());
        this.add(tableScrollPane, BorderLayout.CENTER);

        addSearch();

        addButtons();

        setTitle("查询爬取结果");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(mainFrame);
    }

    private void addButtons() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton preButton = new JButton("上一页");
        preButton.addActionListener(ae -> {
            try {
                searcher.prevPage();
            } catch (IOException e) {
                log.log(Level.WARNING, "", e);
                JOptionPane.showInternalMessageDialog(null, "查询上一页失败" + e.getMessage(), "警告", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        buttonPanel.add(preButton);

        JButton nextButton = new JButton("下一页");
        nextButton.addActionListener(ae -> {
            try {
                searcher.nextPage();
            } catch (IOException e) {
                log.log(Level.WARNING, "", e);
                JOptionPane.showInternalMessageDialog(null, "查询下一页失败" + e.getMessage(), "警告", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        buttonPanel.add(nextButton);

        searchTotalLabel = new JLabel("总数: 0");
        buttonPanel.add(searchTotalLabel);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void updateTotal(int total, int pageNum) {
        SwingUtilities.invokeLater(() -> searchTotalLabel.setText(String.format("第%d页 总数: %d", pageNum, total)));
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
        SwingUtilities.invokeLater(() -> {
            tableModel.setValueAt(doc.get(IndexFieldName.parentUrl.name()), row, 0);
            tableModel.setValueAt(doc.get(IndexFieldName.url.name()), row, 1);
            String type = doc.get(IndexFieldName.type.name());
            tableModel.setValueAt(type, row, 2);
            String ext = doc.get(IndexFieldName.ext.name());
            tableModel.setValueAt(ext, row, 3);
            String content = doc.get(IndexFieldName.content.name());
            tableModel.setValueAt(content, row, 4);
        });
    }

    private void removeRow(int row) {
        SwingUtilities.invokeLater(() -> {
            for (int col = 0; col < tableModel.getColumnCount(); col++) {
                tableModel.setValueAt(null, row, col);
            }
        });
    }

    private void initTable() {
        this.table.setDragEnabled(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setCellSelectionEnabled(true);
        this.table.setTableHeader(new JTableHeader(this.table.getColumnModel()));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() != 2) {
                    return;
                }
                int row = table.rowAtPoint(me.getPoint());
                int col = table.columnAtPoint(me.getPoint());
                if (row < 0 || col < 0) {
                    return;
                }

                Object content = tableModel.getValueAt(row, col);
                if (content == null) {
                    return;
                }

                // 父子链接调用主窗口浏览器浏览
                if (col == 0 || col == 1) {
                    try {
                        mainFrame.getRobot().get((String) content, 10);
                        toolPanel.updateUrl((String) content);
                    } catch (Exception ex) {
                        log.log(Level.WARNING, "", ex);
                        JOptionPane.showInternalMessageDialog(null, "加载失败e=" + ex.getMessage(), "警告", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else if (col == 2 || col == 3) {
                    SwingUtilities.invokeLater(() -> new TextFrame(QueryFrame.this, "文本", (String) content).setVisible(true));
                } else if (col == 4) {
                    String type = (String) tableModel.getValueAt(row, 2);
                    String path = tableModel.getContent(row);

                    if (type.equalsIgnoreCase(IndexType.image.name())) {
                        if (AppConfig.isSystemBrowser()) {
                            try {
                                Desktop.getDesktop().open(new File(path));
                            } catch (IOException ex) {
                                log.log(Level.WARNING, "", ex);
                                JOptionPane.showInternalMessageDialog(null, "图片显示失败e=" + ex.getMessage(), "警告", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } else {
                            SwingUtilities.invokeLater(() -> new ImageFrame(QueryFrame.this, path).setVisible(true));
                        }
                    } else if (type.equalsIgnoreCase(IndexType.doc.name())) {
                        ForkJoinPool.commonPool().execute(() -> {
                            String txt = "";
                            try {
                                txt = Files.readString(Path.of(path));
                            } catch (IOException ex) {
                                log.log(Level.WARNING, "", ex);
                                JOptionPane.showInternalMessageDialog(null, "加载失败e=" + ex.getMessage(), "警告", JOptionPane.INFORMATION_MESSAGE);
                            }
                            final String fTxt = txt;
                            SwingUtilities.invokeLater(() -> new TextFrame(QueryFrame.this, "文本", fTxt).setVisible(true));
                        });
                    } else {
                        SwingUtilities.invokeLater(() -> new TextFrame(QueryFrame.this, "文本", path).setVisible(true));
                    }
                }
            }
        });
    }

    private void addSearch() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        addSearchInput(searchPanel);
        addSearchType(searchPanel);

        add(searchPanel, BorderLayout.NORTH);
    }

    private void addSearchType(JPanel searchPanel) {
        typeCombo = new JComboBox<>(tableModel.getColumnNames());

        searchPanel.add(typeCombo);
    }

    private void addSearchInput(JPanel searchPanel) {
        JTextField searchInputField = new JTextField("", 50);
        searchInputField.setFocusTraversalKeysEnabled(false);

        searchInputField.addActionListener(ae -> {
            String keyword = ae.getActionCommand();

            keyword = keyword == null ? "" : keyword;

            String type = (String) typeCombo.getSelectedItem();

            final String fKeyword = keyword;
            ForkJoinPool.commonPool().execute(() -> {
                try {
                    searcher.search(type, fKeyword);
                } catch (Exception e) {
                    log.log(Level.WARNING, "", e);
                    JOptionPane.showInternalMessageDialog(null, "搜索失败e=" + e.getMessage(), "警告", JOptionPane.INFORMATION_MESSAGE);
                }
            });
        });

        searchPanel.add(searchInputField);
    }

    @Override
    public void dispose() {
        super.dispose();

        if (searcher != null) {
            try {
                searcher.close();
            } catch (IOException ignore) {
            }
        }
    }
}
