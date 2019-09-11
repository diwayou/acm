package com.diwayou.web.ui.query;

import javax.swing.table.AbstractTableModel;

public class ResultTableModel extends AbstractTableModel {

    /**
     * The current row count.
     */
    private int rows;

    /**
     * The column count.
     */
    private int cols = 5;

    /**
     * The table data.
     */
    private Object[][] data;

    /**
     * The table column classes.
     */
    private Class<?>[] columns = new Class<?>[]{String.class, String.class, String.class, String.class, String.class};

    private String[] columnNames = new String[]{"父链接", "链接", "类型", "后缀", "内容"};

    private String[] contentCache;

    private static final String CONTENT_TIP = "点击获取详细内容";

    public ResultTableModel(int rows) {
        this.rows = rows;
        this.data = new Object[rows][this.cols];
        this.contentCache = new String[rows];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return this.columns[columnIndex];
    }

    @Override
    public int getColumnCount() {
        return this.cols;
    }

    @Override
    public int getRowCount() {
        return this.rows;
    }

    @Override
    public Object getValueAt(int row, int col) {
        return this.data[row][col];
    }

    @Override
    public String getColumnName(int column) {
        return this.columnNames[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        if (col == 4 && value != null) {
            this.data[row][col] = CONTENT_TIP;
            this.contentCache[row] = (String) value;
        } else {
            this.data[row][col] = value;
        }

        this.fireTableCellUpdated(row, col);
    }

    public String getContent(int row) {
        return this.contentCache[row];
    }

    public String[] getColumnNames() {
        return columnNames;
    }
}
