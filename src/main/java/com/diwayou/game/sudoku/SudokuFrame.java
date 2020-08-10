package com.diwayou.game.sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class SudokuFrame extends JFrame {

    private JPanel buttonSelectionPanel;
    private SudokuPanel sPanel;

    public SudokuFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Sudoku");
        this.setMinimumSize(new Dimension(800, 600));

        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("Game");
        JMenu newGame = new JMenu("New Game");

        JMenuItem fourByFourGame = new JMenuItem(SudokuPuzzleType.FOUR_BY_FOUR.toString());
        fourByFourGame.addActionListener(new NewGameListener(SudokuPuzzleType.FOUR_BY_FOUR, 30));
        JMenuItem sixBySixGame = new JMenuItem(SudokuPuzzleType.SIX_BY_SIX.toString());
        sixBySixGame.addActionListener(new NewGameListener(SudokuPuzzleType.SIX_BY_SIX, 30));
        JMenuItem nineByNineGame = new JMenuItem(SudokuPuzzleType.NINE_BY_NINE.toString());
        nineByNineGame.addActionListener(new NewGameListener(SudokuPuzzleType.NINE_BY_NINE, 26));
        JMenuItem twelveByTwelveGame = new JMenuItem(SudokuPuzzleType.TWELVE_BY_TWELVE.toString());
        twelveByTwelveGame.addActionListener(new NewGameListener(SudokuPuzzleType.TWELVE_BY_TWELVE, 20));
		
		/*
		 * need to include this when solving algorithm is improved
		 JMenuItem sixteenBySizteenGame = new JMenuItem("16 By 16 Game");
		sixteenBySizteenGame.addActionListener(new NewGameListener(SudokuPuzzleType.SIXTEENBYSIXTEEN,16));
		*/
        newGame.add(fourByFourGame);
        newGame.add(sixBySixGame);
        newGame.add(nineByNineGame);
        newGame.add(twelveByTwelveGame);
        //newGame.add(sixteenBySizteenGame);
        file.add(newGame);
        menuBar.add(file);
        this.setJMenuBar(menuBar);

        JPanel windowPanel = new JPanel();
        windowPanel.setLayout(new FlowLayout());
        windowPanel.setPreferredSize(new Dimension(800, 600));

        buttonSelectionPanel = new JPanel();
        buttonSelectionPanel.setPreferredSize(new Dimension(90, 500));

        sPanel = new SudokuPanel();

        windowPanel.add(sPanel);
        windowPanel.add(buttonSelectionPanel);
        this.add(windowPanel);

        rebuildInterface(SudokuPuzzleType.NINE_BY_NINE, 26);
    }

    public void rebuildInterface(SudokuPuzzleType puzzleType, int fontSize) {
        SudokuPuzzle generatedPuzzle = new SudokuGenerator().generateRandomSudoku(puzzleType);
        sPanel.newSudokuPuzzle(generatedPuzzle);
        sPanel.setFontSize(fontSize);
        buttonSelectionPanel.removeAll();
        for (String value : generatedPuzzle.getValidValues()) {
            JButton b = new JButton(value);
            b.setPreferredSize(new Dimension(40, 40));
            b.addActionListener(sPanel.new NumActionListener());
            buttonSelectionPanel.add(b);
        }
        sPanel.repaint();
        buttonSelectionPanel.revalidate();
        buttonSelectionPanel.repaint();
    }

    private class NewGameListener implements ActionListener {

        private SudokuPuzzleType puzzleType;
        private int fontSize;

        public NewGameListener(SudokuPuzzleType puzzleType, int fontSize) {
            this.puzzleType = puzzleType;
            this.fontSize = fontSize;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            rebuildInterface(puzzleType, fontSize);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SudokuFrame frame = new SudokuFrame();
                frame.setVisible(true);
            }
        });
    }
}
