package com.diwayou.game.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Tetris extends JPanel {

    private static final int BlockSize = 10;
    private static final int BlockWidth = 16;
    private static final int BlockHeight = 26;
    private static final int TimeDelay = 1000;

    private static final String[] AuthorInfo = {
            "制作人：", "HelloClyde"
    };

    // 存放已经固定的方块
    private boolean[][] blockMap = new boolean[BlockHeight][BlockWidth];

    // 分数
    private int score = 0;

    //是否暂停
    private boolean isPause = false;

    // 7种形状
    static boolean[][][] shape = BlockV4.Shape;

    // 下落方块的位置,左上角坐标
    private Point nowBlockPos;

    // 当前方块矩阵
    private boolean[][] nowBlockMap;
    // 下一个方块矩阵
    private boolean[][] nextBlockMap;
    /**
     * 范围[0,28) 7种，每种有4种旋转状态，共4*7=28 %4获取旋转状态 /4获取形状
     */
    private int nextBlockState;
    private int nowBlockState;

    //计时器
    private Timer timer;

    public Tetris() {
        this.initial();
        timer = new Timer(Tetris.TimeDelay, this.TimerListener);
        timer.start();
        this.addKeyListener(this.KeyListener);
    }

    public void setMode(String mode) {
        if (mode.equals("v6")) {
            Tetris.shape = BlockV6.Shape;
        } else {
            Tetris.shape = BlockV4.Shape;
        }
        this.initial();
        this.repaint();
    }

    /**
     * 新的方块落下时的初始化
     */
    private void getNextBlock() {
        // 将已经生成好的下一次方块赋给当前方块
        this.nowBlockState = this.nextBlockState;
        this.nowBlockMap = this.nextBlockMap;
        // 再次生成下一次方块
        this.nextBlockState = this.createNewBlockState();
        this.nextBlockMap = this.getBlockMap(nextBlockState);
        // 计算方块位置
        this.nowBlockPos = this.calNewBlockInitPos();
    }

    /**
     * 判断正在下落的方块和墙、已经固定的方块是否有接触
     *
     * @return
     */
    private boolean isTouch(boolean[][] srcNextBlockMap, Point srcNextBlockPos) {
        for (int i = 0; i < srcNextBlockMap.length; i++) {
            for (int j = 0; j < srcNextBlockMap[i].length; j++) {
                if (srcNextBlockMap[i][j]) {
                    if (srcNextBlockPos.y + i >= Tetris.BlockHeight || srcNextBlockPos.x + j < 0 || srcNextBlockPos.x + j >= Tetris.BlockWidth) {
                        return true;
                    } else {
                        if (srcNextBlockPos.y + i < 0) {
                            continue;
                        } else {
                            if (this.blockMap[srcNextBlockPos.y + i][srcNextBlockPos.x + j]) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 固定方块到地图
     */
    private boolean fixBlock() {
        for (int i = 0; i < this.nowBlockMap.length; i++) {
            for (int j = 0; j < this.nowBlockMap[i].length; j++) {
                if (this.nowBlockMap[i][j])
                    if (this.nowBlockPos.y + i < 0)
                        return false;
                    else
                        this.blockMap[this.nowBlockPos.y + i][this.nowBlockPos.x + j] = this.nowBlockMap[i][j];
            }
        }
        return true;
    }

    /**
     * 计算新创建的方块的初始位置
     *
     * @return 返回坐标
     */
    private Point calNewBlockInitPos() {
        return new Point(Tetris.BlockWidth / 2 - this.nowBlockMap[0].length / 2, -this.nowBlockMap.length);
    }

    /**
     * 初始化
     */
    public void initial() {
        //清空Map
        for (int i = 0; i < this.blockMap.length; i++) {
            for (int j = 0; j < this.blockMap[i].length; j++) {
                this.blockMap[i][j] = false;
            }
        }
        //清空分数
        this.score = 0;
        // 初始化第一次生成的方块和下一次生成的方块
        this.nowBlockState = this.createNewBlockState();
        this.nowBlockMap = this.getBlockMap(this.nowBlockState);
        this.nextBlockState = this.createNewBlockState();
        this.nextBlockMap = this.getBlockMap(this.nextBlockState);
        // 计算方块位置
        this.nowBlockPos = this.calNewBlockInitPos();
        this.repaint();
    }

    public void setPause(boolean value) {
        this.isPause = value;
        if (this.isPause) {
            this.timer.stop();
        } else {
            this.timer.restart();
        }
        this.repaint();
    }

    /**
     * 随机生成新方块状态
     */
    private int createNewBlockState() {
        int Sum = Tetris.shape.length * 4;
        return (int) (Math.random() * 1000) % Sum;
    }

    private boolean[][] getBlockMap(int BlockState) {
        int Shape = BlockState / 4;
        int Arc = BlockState % 4;
        System.out.println(BlockState + "," + Shape + "," + Arc);
        return this.rotateBlock(Tetris.shape[Shape], Arc);
    }

    /**
     * 原算法
     *
     * 旋转方块Map，使用极坐标变换,注意源矩阵不会被改变
     * 使用round解决double转换到int精度丢失导致结果不正确的问题
     *
     * @param BlockMap
     *            需要旋转的矩阵
     * @param angel
     *            rad角度，应该为pi/2的倍数
     * @return 转换完成后的矩阵引用

    private boolean[][] RotateBlock(boolean[][] BlockMap, double angel) {
    // 获取矩阵宽高
    int Heigth = BlockMap.length;
    int Width = BlockMap[0].length;
    // 新矩阵存储结果
    boolean[][] ResultBlockMap = new boolean[Heigth][Width];
    // 计算旋转中心
    float CenterX = (Width - 1) / 2f;
    float CenterY = (Heigth - 1) / 2f;
    // 逐点计算变换后的位置
    for (int i = 0; i < BlockMap.length; i++) {
    for (int j = 0; j < BlockMap[i].length; j++) {
    //计算相对于旋转中心的坐标
    float RelativeX = j - CenterX;
    float RelativeY = i - CenterY;
    float ResultX = (float) (Math.cos(angel) * RelativeX - Math.sin(angel) * RelativeY);
    float ResultY = (float) (Math.cos(angel) * RelativeY + Math.sin(angel) * RelativeX);
    // 调试信息
    //System.out.println("RelativeX:" + RelativeX + "RelativeY:" + RelativeY);
    //System.out.println("ResultX:" + ResultX + "ResultY:" + ResultY);

    //将结果坐标还原
    Point OrginPoint = new Point(Math.round(CenterX + ResultX), Math.round(CenterY + ResultY));
    ResultBlockMap[OrginPoint.y][OrginPoint.x] = BlockMap[i][j];
    }
    }
    return ResultBlockMap;
    }
     **/

    /**
     * @param shape 7种图形之一
     * @param time  旋转次数
     * @return https://blog.csdn.net/janchin/article/details/6310654  翻转矩阵
     */

    private boolean[][] rotateBlock(boolean[][] shape, int time) {
        if (time == 0) {
            return shape;
        }
        int height = shape.length;
        int width = shape[0].length;
        boolean[][] resultMap = new boolean[height][width];
        int tmpH = height - 1, tmpW = 0;
        for (int i = 0; i < height && tmpW < width; i++) {
            for (int j = 0; j < width && tmpH > -1; j++) {
                resultMap[i][j] = shape[tmpH][tmpW];
                tmpH--;
            }
            tmpH = height - 1;
            tmpW++;
        }
        for (int i = 1; i < time; i++) {
            resultMap = rotateBlock(resultMap, 0);
        }
        return resultMap;
    }

    /**
     * 测试方法，测试旋转函数
     *
     * @param args
     */
    static public void main(String... args) {
        boolean[][] SrcMap = Tetris.shape[3];
        Tetris.showMap(SrcMap);
		/*
		for (int i = 0;i < 7;i ++){
			System.out.println(i);
			Tetris.ShowMap(Tetris.Shape[i]);
		}
		*/

        Tetris tetris = new Tetris();
        boolean[][] result = tetris.rotateBlock(SrcMap, 1);
        Tetris.showMap(result);

    }

    /**
     * 测试方法，显示矩阵
     *
     * @param srcMap
     */
    static private void showMap(boolean[][] srcMap) {
        System.out.println("-----");
        for (int i = 0; i < srcMap.length; i++) {
            for (int j = 0; j < srcMap[i].length; j++) {
                if (srcMap[i][j])
                    System.out.print("*");
                else
                    System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("-----");
    }

    /**
     * 绘制游戏界面
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 画墙
        for (int i = 0; i < Tetris.BlockHeight + 1; i++) {
            g.drawRect(0, i * Tetris.BlockSize, Tetris.BlockSize, Tetris.BlockSize);
            g.drawRect((Tetris.BlockWidth + 1) * Tetris.BlockSize, i * Tetris.BlockSize, Tetris.BlockSize, Tetris.BlockSize);
        }
        for (int i = 0; i < Tetris.BlockWidth; i++) {
            g.drawRect((1 + i) * Tetris.BlockSize, Tetris.BlockHeight * Tetris.BlockSize, Tetris.BlockSize, Tetris.BlockSize);
        }
        // 画当前方块
        for (int i = 0; i < this.nowBlockMap.length; i++) {
            for (int j = 0; j < this.nowBlockMap[i].length; j++) {
                if (this.nowBlockMap[i][j])
                    g.fillRect((1 + this.nowBlockPos.x + j) * Tetris.BlockSize, (this.nowBlockPos.y + i) * Tetris.BlockSize,
                            Tetris.BlockSize, Tetris.BlockSize);
            }
        }
        // 画已经固定的方块
        for (int i = 0; i < Tetris.BlockHeight; i++) {
            for (int j = 0; j < Tetris.BlockWidth; j++) {
                if (this.blockMap[i][j])
                    g.fillRect(Tetris.BlockSize + j * Tetris.BlockSize, i * Tetris.BlockSize, Tetris.BlockSize,
                            Tetris.BlockSize);
            }
        }
        //绘制下一个方块
        for (int i = 0; i < this.nextBlockMap.length; i++) {
            for (int j = 0; j < this.nextBlockMap[i].length; j++) {
                if (this.nextBlockMap[i][j])
                    g.fillRect(190 + j * Tetris.BlockSize, 30 + i * Tetris.BlockSize, Tetris.BlockSize, Tetris.BlockSize);
            }
        }
        // 绘制其他信息
        g.drawString("游戏分数:" + this.score, 190, 10);
        for (int i = 0; i < Tetris.AuthorInfo.length; i++) {
            g.drawString(Tetris.AuthorInfo[i], 190, 100 + i * 20);
        }

        //绘制暂停
        if (this.isPause) {
            g.setColor(Color.white);
            g.fillRect(70, 100, 50, 20);
            g.setColor(Color.black);
            g.drawRect(70, 100, 50, 20);
            g.drawString("PAUSE", 75, 113);
        }
    }

    /**
     * @return
     */
    private int clearLines() {
        int lines = 0;
        for (int i = 0; i < this.blockMap.length; i++) {
            boolean isLine = true;
            for (int j = 0; j < this.blockMap[i].length; j++) {
                if (!this.blockMap[i][j]) {
                    isLine = false;
                    break;
                }
            }
            if (isLine) {
                for (int k = i; k > 0; k--) {
                    this.blockMap[k] = this.blockMap[k - 1];
                }
                this.blockMap[0] = new boolean[Tetris.BlockWidth];
                lines++;
            }
        }
        return lines;
    }

    // 定时器监听
    ActionListener TimerListener = actionEvent -> {
        if (Tetris.this.isTouch(Tetris.this.nowBlockMap, new Point(Tetris.this.nowBlockPos.x, Tetris.this.nowBlockPos.y + 1))) {
            if (Tetris.this.fixBlock()) {
                Tetris.this.score += Tetris.this.clearLines() * 10;
                Tetris.this.getNextBlock();
            } else {
                JOptionPane.showMessageDialog(Tetris.this.getParent(), "GAME OVER");
                Tetris.this.initial();
            }
        } else {
            Tetris.this.nowBlockPos.y++;
        }

        Tetris.this.repaint();
    };

    //按键监听
    java.awt.event.KeyListener KeyListener = new java.awt.event.KeyListener() {

        @Override
        public void keyPressed(KeyEvent e) {
            if (!isPause) {
                Point desPoint;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_DOWN:
                        desPoint = new Point(Tetris.this.nowBlockPos.x, Tetris.this.nowBlockPos.y + 1);
                        if (!Tetris.this.isTouch(Tetris.this.nowBlockMap, desPoint)) {
                            Tetris.this.nowBlockPos = desPoint;
                        }
                        break;
                    case KeyEvent.VK_UP:
                        boolean[][] TurnBlock = Tetris.this.rotateBlock(Tetris.this.nowBlockMap, 1);
                        if (!Tetris.this.isTouch(TurnBlock, Tetris.this.nowBlockPos)) {
                            Tetris.this.nowBlockMap = TurnBlock;
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        desPoint = new Point(Tetris.this.nowBlockPos.x + 1, Tetris.this.nowBlockPos.y);
                        if (!Tetris.this.isTouch(Tetris.this.nowBlockMap, desPoint)) {
                            Tetris.this.nowBlockPos = desPoint;
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        desPoint = new Point(Tetris.this.nowBlockPos.x - 1, Tetris.this.nowBlockPos.y);
                        if (!Tetris.this.isTouch(Tetris.this.nowBlockMap, desPoint)) {
                            Tetris.this.nowBlockPos = desPoint;
                        }
                        break;
                }

                repaint();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }
    };
}
