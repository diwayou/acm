package com.diwayou.acm.code;

import java.util.Arrays;
import java.util.List;

/**
 * {@link javax.swing.table.DefaultTableModel}
 * 从这个类看到的算法，感觉还挺有意思的
 */
public class TableMoveRowStudy {

    private List<String> data;

    public TableMoveRowStudy(List<String> data) {
        this.data = data;
    }

    /**
     * Moves one or more rows from the inclusive range <code>start</code> to
     * <code>end</code> to the <code>to</code> position in the model.
     * After the move, the row that was at index <code>start</code>
     * will be at index <code>to</code>.
     * This method will send a <code>tableChanged</code> notification
     * message to all the listeners.
     *
     * <pre>
     *  Examples of moves:
     *
     *  1. moveRow(1,3,5);
     *          a|B|C|D|e|f|g|h|i|j|k   - before
     *          a|e|f|g|h|B|C|D|i|j|k   - after
     *
     *  2. moveRow(6,7,1);
     *          a|b|c|d|e|f|G|H|i|j|k   - before
     *          a|G|H|b|c|d|e|f|i|j|k   - after
     *  </pre>
     *
     * @param start the starting row index to be moved
     * @param end   the ending row index to be moved
     * @param to    the destination of the rows to be moved
     * @throws ArrayIndexOutOfBoundsException if any of the elements
     *                                        would be moved out of the table's range
     */
    public void moveRow(int start, int end, int to) {
        int shift = to - start;
        int first, last;
        if (shift < 0) {
            first = to;
            last = end;
        } else {
            first = start;
            last = to + end - start;
        }
        rotate(data, first, last + 1, shift);
    }

    private static int gcd(int i, int j) {
        return (j == 0) ? i : gcd(j, i % j);
    }

    private static void rotate(List<String> v, int a, int b, int shift) {
        int size = b - a;
        int r = size - shift;
        int g = gcd(size, r);
        for (int i = 0; i < g; i++) {
            int to = i;
            String tmp = v.get(a + to);
            for (int from = (to + r) % size; from != i; from = (to + r) % size) {
                v.set(a + to, v.get(a + from));
                to = from;
            }
            v.set(a + to, tmp);
        }
    }

    public void print() {
        System.out.println(data);
    }

    public static void main(String[] args) {
        TableMoveRowStudy tableMoveRowStudy = new TableMoveRowStudy(Arrays.asList("a|B|C|D|e|f|g|h|i|j|k".split("\\|")));
        tableMoveRowStudy.moveRow(1, 3, 5);
        tableMoveRowStudy.print();
    }
}
