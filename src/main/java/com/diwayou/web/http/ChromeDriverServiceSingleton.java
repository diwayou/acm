package com.diwayou.web.http;

import com.diwayou.web.common.RuntimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.chrome.ChromeDriverService;

import java.io.File;
import java.io.IOException;

public class ChromeDriverServiceSingleton {

    private static ChromeDriverServiceSingleton instance = new ChromeDriverServiceSingleton();

    private ChromeDriverService chromeDriverService;

    public ChromeDriverServiceSingleton() {
        String chromeDriverPath = System.getProperty(Constant.CHROME_DRIVER);

        if (StringUtils.isBlank(chromeDriverPath)) {
            throw new IllegalArgumentException("使用该类之前必须设置Chrome Driver的地址,例如System.setProperty(Constant.CHROME_DRIVER, \"path\");");
        }

        createAndStartService(chromeDriverPath);
    }

    private void createAndStartService(String chromeDriverPath) {
        chromeDriverService = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(chromeDriverPath))
                .usingAnyFreePort()
                .build();

        try {
            chromeDriverService.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 程序使用完毕关闭，应该目前够用
        RuntimeUtil.addShutdownHook((Void) -> chromeDriverService.stop());
    }

    public static ChromeDriverServiceSingleton getInstance() {
        return instance;
    }

    public ChromeDriverService getChromeDriverService() {
        return chromeDriverService;
    }
}
