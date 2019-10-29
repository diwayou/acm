package com.diwayou.game.gobang.core;

import com.diwayou.game.gobang.entity.ChessBoard;
import com.diwayou.game.gobang.entity.ChessOwner;
import com.diwayou.game.gobang.entity.ChessPiece;
import com.diwayou.game.gobang.entity.ScoreChessPiece;

import java.util.ArrayList;
import java.util.List;

public class SimpleAi {

    /**
     * 棋盘
     */
    private ChessOwner[][] chess;

    private ChessBoard chessBoard;

    public SimpleAi(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    /**
     * 将点添加到集合中，过滤掉重复的位置
     *
     * @param allMayChessPiece 目标集合
     * @param x              坐标x
     * @param y              坐标y
     */
    private void addToList(List<ScoreChessPiece> allMayChessPiece, int x, int y) {
        int sign = 0;
        for (ChessPiece chessPiece : allMayChessPiece) {
            if (chessPiece.getX() == x && chessPiece.getY() == y) {
                sign = 1;
                break;
            }
        }

        if (sign == 0) {
            allMayChessPiece.add(new ScoreChessPiece(x, y));
        }
    }

    /**
     * AI对棋盘分析，控制器的核心方法
     * 本算法计算出的位置对双方都是有益的
     *
     * @return
     */
    public ChessPiece explore() {
        this.chess = chessBoard.getChess();

        // 得到可行位置的集合
        List<ScoreChessPiece> allMayChessPiece = getAllMayLocation();

        if (allMayChessPiece.isEmpty()) {
            return new ChessPiece(chess.length / 2, chess.length / 2);
        }

        // 所有得分最大且相同的位置
        //打分时可能存在分数相同的位置，将这个位置保存起来随机落子
        List<ScoreChessPiece> allMaxChessPiece = new ArrayList<>();

        // 对每个可落子的空位计算分数
        int max = 0;//最大分数

        //遍历可行位置集合
        for (ScoreChessPiece chessPiece : allMayChessPiece) {

            //计算位置得分并设置位置分数
            int score = getScore(chessPiece.getX(), chessPiece.getY());
            chessPiece.setScore(score);

            //判断
            if (score > max) max = score;

            //如果socre是当前最大值且不是0
            //如果allMaxLocation集合中第一个元素值小于max
            //先清空，然后再添加该位置
            //否则，直接添加该位置
            if (max != 0 && score == max) {
                if (allMaxChessPiece.size() > 0)
                    if (allMaxChessPiece.get(0).getScore() < max)
                        allMaxChessPiece.clear();
                allMaxChessPiece.add(chessPiece);
            }

            System.out.println("x=" + chessPiece.getX() + " y=" + chessPiece.getY() + " score=" + score);
        }

        //从最高分集合中随机抽取一个位置
        ChessPiece pos = allMaxChessPiece.get((int) (Math.random() * allMaxChessPiece.size()));

        System.out.println("机器落子:行：" + (pos.getX() + 1) + " 列:" + (pos.getY() + 1));

        //返回分析的位置
        return new ChessPiece(pos.getX(), pos.getY());
    }

    /**
     * 获取可行位置集合
     * 对每个非空位置，将其四周的位置添加到集合中
     * 注意去掉重复的位置
     *
     * @return 位置集合
     */
    private List<ScoreChessPiece> getAllMayLocation() {

        List<ScoreChessPiece> allMayChessPiece = new ArrayList<>();

        // 搜索棋盘获取可行棋的点
        for (int i = 0; i < chess.length; i++)
            for (int j = 0; j < chess.length; j++) {
                if (chess[i][j] != null) {
                    if (j != 0 && chess[i][j - 1] == null)
                        addToList(allMayChessPiece, i, j - 1);
                    if (j != (chess.length - 1) && chess[i][j + 1] == null)
                        addToList(allMayChessPiece, i, j + 1);
                    if (i != 0 && j != 0 && chess[i - 1][j - 1] == null)
                        addToList(allMayChessPiece, i - 1, j - 1);
                    if (i != 0 && chess[i - 1][j] == null)
                        addToList(allMayChessPiece, i - 1, j);
                    if (i != 0 && j != (chess.length - 1) && chess[i - 1][j + 1] == null)
                        addToList(allMayChessPiece, i - 1, j + 1);
                    if (i != (chess.length - 1) && j != 0 && chess[i + 1][j - 1] == null)
                        addToList(allMayChessPiece, i + 1, j - 1);
                    if (i != (chess.length - 1) && chess[i + 1][j] == null)
                        addToList(allMayChessPiece, i + 1, j);
                    if (i != (chess.length - 1) && j != (chess.length - 1) && chess[i + 1][j + 1] == null)
                        addToList(allMayChessPiece, i + 1, j + 1);
                }
            }

        return allMayChessPiece;
    }

    /**
     * 局势评估函数
     * 评估该点的得分
     *
     * @param x 坐标x
     * @param y 坐标y
     * @return 分数
     */
    public int getScore(int x, int y) {
        //使用换位思考思想
        //以己方棋子和对方棋子模拟落子计算分数和
        int xScore = getXScore(x, y, ChessOwner.playerOne) + getXScore(x, y, ChessOwner.playerTwo);
        int yScore = getYScore(x, y, ChessOwner.playerOne) + getYScore(x, y, ChessOwner.playerTwo);
        int skewScore1 = getSkewScore1(x, y, ChessOwner.playerOne) + getSkewScore1(x, y, ChessOwner.playerTwo);
        int skewScore2 = getSkewScore2(x, y, ChessOwner.playerOne) + getSkewScore2(x, y, ChessOwner.playerTwo);
//		int xScore = getXScore(x, y, 1);
//		int yScore = getYScore(x, y, 1);
//		int skewScore1 = getSkewScore1(x, y, 1);
//		int skewScore2 = getSkewScore2(x, y, 1);
        return xScore + yScore + skewScore1 + skewScore2;
    }

    /**
     * 根据棋型计算得分
     *
     * @param count1      连子个数
     * @param leftStatus  左侧封堵情况 1:空位，2：对方或墙
     * @param rightStatus 右侧封堵情况 1:空位，2：对方或墙
     * @return 分数
     */
    private int getScoreBySituation(int count1, int leftStatus, int rightStatus) {
        int score = 0;

        // 五子情况
        if (count1 >= 5)
            score += 200000;// 赢了

        // 四子情况
        if (count1 == 4) {
            if (leftStatus == 1 && rightStatus == 1)
                score += 50000;
            if ((leftStatus == 2 && rightStatus == 1) || (leftStatus == 1 && rightStatus == 2))
                score += 3000;
            if (leftStatus == 2 && rightStatus == 2)
                score += 1000;
        }

        //三子情况
        if (count1 == 3) {
            if (leftStatus == 1 && rightStatus == 1)
                score += 3000;
            if ((leftStatus == 2 && rightStatus == 1) || (leftStatus == 1 && rightStatus == 2))
                score += 1000;
            if (leftStatus == 2 && rightStatus == 2)
                score += 500;
        }

        //二子情况
        if (count1 == 2) {
            if (leftStatus == 1 && rightStatus == 1)
                score += 500;
            if ((leftStatus == 2 && rightStatus == 1) || (leftStatus == 1 && rightStatus == 2))
                score += 200;
            if (leftStatus == 2 && rightStatus == 2)
                score += 100;
        }

        //一子情况
        if (count1 == 1) {
            if (leftStatus == 1 && rightStatus == 1)
                score += 100;
            if ((leftStatus == 2 && rightStatus == 1) || (leftStatus == 1 && rightStatus == 2))
                score += 50;
            if (leftStatus == 2 && rightStatus == 2)
                score += 30;
        }

        return score;
    }

    /**
     * 获取该空位在横向上的得分
     *
     * @param x 位置横坐标
     * @param y 位置纵坐标
     * @return 评分
     */
    public int getXScore(int x, int y, ChessOwner cur) {
        ChessOwner other;// 对方棋子

        if (cur == ChessOwner.playerOne) {
            other = ChessOwner.playerTwo;
        } else {
            other = ChessOwner.playerOne;
        }

        // 模拟落子
        chess[x][y] = cur;

        //左侧、右侧的状态，用来记录棋型
        int leftStatus = 0;
        int rightStatus = 0;
        int j, count1 = 0;//count1是相连棋子个数

        //扫描记录棋型
        for (j = y; j < chess.length; j++) {
            if (chess[x][j] == cur)
                count1++;
            else {
                if (chess[x][j] == null)
                    rightStatus = 1;// 右侧为空
                if (chess[x][j] == other)
                    rightStatus = 2;// 右侧被对方堵住
                break;
            }
        }
        for (j = y - 1; j >= 0; j--) {
            if (chess[x][j] == cur)
                count1++;
            else {
                if (chess[x][j] == null)
                    leftStatus = 1;// 左侧为空
                if (chess[x][j] == other)
                    leftStatus = 2;// 左侧被对方堵住
                break;
            }
        }

        // 恢复
        chess[x][y] = null;

        //根据棋型计算空位分数
        return getScoreBySituation(count1, leftStatus, rightStatus);
    }

    /**
     * 获取该点在纵向上的得分
     *
     * @param x
     * @param y
     */
    public int getYScore(int x, int y, ChessOwner cur) {
        ChessOwner other;// 对方棋子

        if (cur == ChessOwner.playerOne) {
            other = ChessOwner.playerTwo;
        } else {
            other = ChessOwner.playerOne;
        }

        // 模拟落子
        chess[x][y] = cur;

        //左侧、右侧的状态，用来记录棋型
        int topStatus = 0;
        int bottomStatus = 0;
        int i, count1 = 0;

        //扫描记录棋型
        for (i = x; i < chess.length; i++) {
            if (chess[i][y] == cur)
                count1++;
            else {
                if (chess[i][y] == null)
                    bottomStatus = 1;// 下侧为空
                if (chess[i][y] == other)
                    bottomStatus = 2;// 下侧被对方堵住
                break;
            }
        }
        for (i = x - 1; i >= 0; i--) {
            if (chess[i][y] == cur)
                count1++;
            else {
                if (chess[i][y] == null)
                    topStatus = 1;// 上侧为空
                if (chess[i][y] == other)
                    topStatus = 2;// 上侧被对方堵住
                break;
            }
        }
        // 恢复
        chess[x][y] = null;

        return getScoreBySituation(count1, topStatus, bottomStatus);
    }

    /**
     * 正斜向扫描计算得分
     *
     * @param x
     * @param y
     */
    public int getSkewScore1(int x, int y, ChessOwner cur) {
        ChessOwner other;// 对方棋子

        if (cur == ChessOwner.playerOne) {
            other = ChessOwner.playerTwo;
        } else {
            other = ChessOwner.playerOne;
        }

        // 模拟落子
        chess[x][y] = cur;

        // 分数
        int score = 0;

        int topStatus = 0;
        int bottomStatus = 0;
        int i, j, count1 = 0;

        for (i = x, j = y; i < chess.length && j < chess.length; i++, j++) {
            if (chess[i][j] == cur)
                count1++;
            else {
                if (chess[i][j] == null)
                    bottomStatus = 1;// 下侧为空
                if (chess[i][j] == other)
                    bottomStatus = 2;// 下侧被对方堵住
                break;
            }
        }

        for (i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
            if (chess[i][j] == cur)
                count1++;
            else {
                if (chess[i][j] == null)
                    topStatus = 1;// 上侧为空
                if (chess[i][j] == other)
                    topStatus = 2;// 上侧被对方堵住
                break;
            }
        }
        // 恢复
        chess[x][y] = null;

        return getScoreBySituation(count1, topStatus, bottomStatus);
    }

    /**
     * 斜线：从右上到左下
     * 反斜向扫描
     *
     * @param x
     * @param y
     */
    public int getSkewScore2(int x, int y, ChessOwner cur) {
        ChessOwner other;// 对方棋子

        if (cur == ChessOwner.playerOne) {
            other = ChessOwner.playerTwo;
        } else {
            other = ChessOwner.playerOne;
        }

        // 模拟落子
        chess[x][y] = cur;

        // 分数
        int score = 0;

        int topStatus = 0;
        int bottomStatus = 0;
        int i, j;
        // 从右上到左下
        int count1 = 0;
        for (i = x, j = y; i < chess.length && j >= 0; i++, j--) {
            if (chess[i][j] == cur)
                count1++;
            else {
                if (chess[i][j] == null)
                    bottomStatus = 1;// 下侧为空
                if (chess[i][j] == other)
                    bottomStatus = 2;// 下侧被对方堵住
                break;
            }
        }

        for (i = x - 1, j = y + 1; i >= 0 && j < chess.length; i--, j++) {
            if (chess[i][j] == cur)
                count1++;
            else {
                if (chess[i][j] == null)
                    topStatus = 1;// 上侧为空
                if (chess[i][j] == other)
                    topStatus = 2;// 上侧被对方堵住
                break;
            }
        }

        // 恢复
        chess[x][y] = null;
//		System.out.println("count: "+count1+"  "+topStatus+"  "+bottomStatus);
        return getScoreBySituation(count1, topStatus, bottomStatus);
    }
}
