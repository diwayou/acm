package com.diwayou.game.tetris;

import javax.swing.*;

public class TetrisApp extends JFrame {

    private Tetris tetris = new Tetris();

    public TetrisApp() {
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(280, 350);
        this.setTitle("Tetris Remake");
        this.setResizable(false);

        JMenuBar menu = new JMenuBar();
        this.setJMenuBar(menu);

        JMenu gameMenu = new JMenu("游戏");

        JMenuItem newGameItem = gameMenu.add("新游戏");
        newGameItem.addActionListener(e -> TetrisApp.this.tetris.initial());

        JMenuItem pauseItem = gameMenu.add("暂停");
        pauseItem.addActionListener(e -> TetrisApp.this.tetris.setPause(true));

        JMenuItem continueItem = gameMenu.add("继续");
        continueItem.addActionListener(e -> TetrisApp.this.tetris.setPause(false));

        JMenuItem exitItem = gameMenu.add("退出");
        exitItem.addActionListener(e -> System.exit(0));

        JMenu modeMenu = new JMenu("模式");
        JMenuItem v4Item = modeMenu.add("4方块");
        v4Item.addActionListener(e -> TetrisApp.this.tetris.setMode("v4"));
        JMenuItem v6Item = modeMenu.add("6方块");
        v6Item.addActionListener(e -> TetrisApp.this.tetris.setMode("v6"));

        JMenu helpMenu = new JMenu("帮助");
        JMenuItem aboutItem = helpMenu.add("关于");
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(TetrisApp.this, "Tetris Remake Ver 1.0", "关于", JOptionPane.WARNING_MESSAGE));

        menu.add(gameMenu);
        menu.add(modeMenu);
        menu.add(helpMenu);

        this.add(this.tetris);
        this.tetris.setFocusable(true);
    }

    static public void main(String... args) {
        TetrisApp tetrisApp = new TetrisApp();
        tetrisApp.setVisible(true);
    }
}
