package com.diwayou.game.gobang.ai.helper;

import com.diwayou.game.gobang.ai.core.Config;
import com.diwayou.game.gobang.ai.core.GameMap;
import com.diwayou.game.gobang.ai.entity.Counter;
import com.diwayou.game.gobang.ai.entity.Point;
import com.diwayou.game.gobang.ai.enumeration.Color;

import java.util.Collection;
import java.util.Date;

public class ConsolePrinter {

    private long debugTime = new Date().getTime();

    private Counter counter;

    public void init(Counter counter) {
        this.counter = counter;
    }

    public void printInfo(Point point, int value) {
        if (Config.debug) {
            System.out.println(String.format("%s %s: %s count: %s time: %s ms",
                    point.getX(),
                    point.getY(),
                    value,
                    counter.count,
                    new Date().getTime() - debugTime));
        }
    }

    public static void printMap(GameMap gameMap) {
        for (int i = 0; i < Config.size; i++) {
            for (int j = 0; j < Config.size; j++) {
                if (gameMap.getMap()[i][j] == Color.NULL) {
                    System.out.print('□');
                }
                if (gameMap.getMap()[i][j] == Color.BLACK) {
                    System.out.print('×');
                }
                if (gameMap.getMap()[i][j] == Color.WHITE) {
                    System.out.print('●');
                }
            }
            System.out.println();
        }
        System.out.println();
    }


    public static void printMapWithPoints(GameMap gameMap, Collection points) {
        for (int i = 0; i < Config.size; i++) {
            for (int j = 0; j < Config.size; j++) {
                if (points.contains(new Point(i, j))) {
                    System.out.print("★");
                    continue;
                }
                if (gameMap.getMap()[i][j] == Color.NULL) {
                    System.out.print('□');
                }
                if (gameMap.getMap()[i][j] == Color.BLACK) {
                    System.out.print('×');
                }
                if (gameMap.getMap()[i][j] == Color.WHITE) {
                    System.out.print('●');
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
