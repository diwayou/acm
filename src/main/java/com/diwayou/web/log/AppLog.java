package com.diwayou.web.log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AppLog {
    private static final Logger robot = Logger.getLogger("robot");

    private static final Logger crawl = Logger.getLogger("crawl");

    private static final Logger browser = Logger.getLogger("browser");

    public static void init(Path logPath) throws IOException {
        if (!Files.exists(logPath)) {
            Files.createDirectory(logPath);
        }

        FileHandler robotHandler = new FileHandler(logPath.resolve("robot.log").toString());
        robotHandler.setFormatter(new SimpleFormatter());
        robot.addHandler(robotHandler);
        robot.setUseParentHandlers(false);

        FileHandler crawlHandler = new FileHandler(logPath.resolve("crawl.log").toString());
        crawlHandler.setFormatter(new SimpleFormatter());
        crawl.addHandler(crawlHandler);
        crawl.setUseParentHandlers(false);

        FileHandler browserHandler = new FileHandler(logPath.resolve("browser.log").toString());
        browserHandler.setFormatter(new SimpleFormatter());
        browser.addHandler(browserHandler);
        browser.setUseParentHandlers(false);

        FileHandler defaultHandler = new FileHandler(logPath.resolve("default.log").toString());
        defaultHandler.setFormatter(new SimpleFormatter());
        Logger.getLogger("").addHandler(defaultHandler);
    }

    public static Logger getRobot() {
        return robot;
    }

    public static Logger getCrawl() {
        return crawl;
    }

    public static Logger getBrowser() {
        return browser;
    }
}
