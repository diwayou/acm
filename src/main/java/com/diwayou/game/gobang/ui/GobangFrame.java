package com.diwayou.game.gobang.ui;

import com.diwayou.game.gobang.core.ChessController;
import com.diwayou.game.gobang.core.SimpleAi;
import com.diwayou.game.gobang.entity.ChessBoard;
import com.diwayou.game.gobang.entity.ChessOwner;
import com.diwayou.game.gobang.entity.ChessPiece;
import com.diwayou.game.gobang.entity.PlayMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;

public class GobangFrame extends JFrame {

    private ChessBoardUi chessBoardUi;

    private ChessBoard chessBoard;

    private ChessController chessController;

    private PlayMode playMode = PlayMode.humanVsHuman;

    private SimpleAi aiPlayer;

    private ChessOwner first = ChessOwner.playerOne;

    public GobangFrame() throws HeadlessException {
        setTitle("Gobang Fight");

        chessBoard = new ChessBoard();

        chessBoardUi = new ChessBoardUi(chessBoard);

        chessController = new ChessController(chessBoard, chessBoardUi);

        aiPlayer = new SimpleAi(chessBoard);

        JMenuBar bar = new JMenuBar();
        add(bar, BorderLayout.NORTH);

        JMenu file = new JMenu("文件");
        JMenuItem startItem = new JMenuItem("重新开始");
        startItem.addActionListener(e -> chessController.restartBoard());
        file.add(startItem);

        JMenu mode = new JMenu("模式");
        JMenuItem humanItem = new JMenuItem("人人对战");
        humanItem.addActionListener(e -> {
            playMode = PlayMode.humanVsHuman;
            humanItem.setSelected(true);

            chessController.registerListeners(null);
        });
        JMenuItem humanRobotItem = new JMenuItem("人机对战");
        humanRobotItem.addActionListener(e -> {
            playMode = PlayMode.humanVsAi;
            humanRobotItem.setSelected(true);

            chessController.registerListeners(Collections.singletonList(co -> {
                if (co.equals(ChessOwner.playerOne)) {
                    return;
                }

                ChessPiece chessPiece = aiPlayer.explore();

                chessController.showChess(chessPiece.getX(), chessPiece.getY());
            }));
        });
        JMenuItem robotItem = new JMenuItem("AI对战");
        robotItem.addActionListener(e -> {
            playMode = PlayMode.aiVsAi;
            robotItem.setSelected(true);

            chessController.registerListeners(Collections.singletonList(co -> {
                if (co.equals(ChessOwner.playerOne)) {
                    return;
                }

                ChessPiece chessPiece = aiPlayer.explore();

                chessController.showChess(chessPiece.getX(), chessPiece.getY());
            }));
        });
        mode.add(humanItem);
        mode.add(humanRobotItem);
        mode.add(robotItem);
        robotItem.setSelected(true);

        bar.add(file);
        bar.add(mode);

        add(chessBoardUi, BorderLayout.CENTER);

        chessBoardUi.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (mode.equals(PlayMode.aiVsAi)) {
                    ChessPiece chessPiece = aiPlayer.explore();

                    chessController.showChess(chessPiece.getX(), chessPiece.getY());
                } else {
                    chessController.showChess(e);
                }
            }
        });

        //设置frame的相关属性
        setSize(476, 532);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
