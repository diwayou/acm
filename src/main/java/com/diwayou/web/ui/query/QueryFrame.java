package com.diwayou.web.ui.query;

import com.diwayou.web.log.AppLog;
import com.diwayou.web.store.IndexType;
import com.diwayou.web.ui.component.ImageFrame;
import com.diwayou.web.ui.component.TextFrame;
import com.diwayou.web.ui.swing.RobotMainFrame;
import com.diwayou.web.ui.swing.ToolPanel;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
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
            this.searcher = new ResultSearcher(this.tableModel, this);
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

    public void updateTotal(int total) {
        SwingUtilities.invokeLater(() -> searchTotalLabel.setText(String.format("总数: %d", total)));
    }

    private void initTable() {
        this.table.setDragEnabled(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setCellSelectionEnabled(true);
        this.table.setTableHeader(new JTableHeader(this.table.getColumnModel()));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() != 2) {
                    return;
                }
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
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
                        log.log(Level.WARNING, "", e);
                        JOptionPane.showInternalMessageDialog(null, "加载失败e=" + ex.getMessage(), "警告", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else if (col == 2 || col == 3) {
                    SwingUtilities.invokeLater(() -> new TextFrame(QueryFrame.this, "文本", (String) content).setVisible(true));
                } else if (col == 4) {
                    String type = (String) tableModel.getValueAt(row, 2);
                    if (type.equalsIgnoreCase(IndexType.image.name())) {
                        SwingUtilities.invokeLater(() -> {
                            new ImageFrame(QueryFrame.this, "图片", (String) content)
                                    .setVisible(true);
                        });
                    } else {
                        SwingUtilities.invokeLater(() -> new TextFrame(QueryFrame.this, "文本", (String) content).setVisible(true));
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
