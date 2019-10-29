package com.diwayou.game.gobang.ai;

import com.diwayou.game.gobang.ai.core.Config;
import com.diwayou.game.gobang.ai.entity.Point;
import com.diwayou.game.gobang.ai.enumeration.Color;
import com.diwayou.game.gobang.ai.enumeration.Level;
import com.diwayou.game.gobang.ai.helper.MapDriver;
import com.diwayou.game.gobang.ai.helper.WinChecker;
import com.diwayou.game.gobang.ai.player.GomokuPlayer;

public class Main {

    private static Color[][] map = MapDriver.readMap();

    private static Point result = null;

    private static boolean debug = true;

    private static boolean autoRun = false;

    private static boolean updateFile = false;

    private static Color aiColor = Color.BLACK;

    public static void main(String[] args) {
        System.out.println("正在初始化数据...");
        System.out.println("开始计算...");
        if (WinChecker.win(map) != null) {
            System.out.println(WinChecker.win(map) + " win");
            return;
        }
        Config.debug = debug;
        GomokuPlayer gomokuPlayer = new GomokuPlayer(map, Level.VERY_HIGH);
        result = gomokuPlayer.play(aiColor).getPoint();
        System.out.println(result);
        map[result.getX()][result.getY()] = aiColor;
        if (updateFile) {
            MapDriver.printMap(map);
        }
        if (autoRun) {
            loop();
        }
    }

    private static void loop() {
        aiColor = aiColor.getOtherColor();
        main(null);
    }
}
