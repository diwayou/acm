package com.diwayou.acm.http;

import com.diwayou.acm.util.StdDraw;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class HttpRobot {

    public static void main(String[] args) throws IOException, InterruptedException {
        String chromeDriverPath = "D:\\opensource\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);

        ChromeDriverService chromeDriverService = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(chromeDriverPath))
                .usingAnyFreePort()
                .build();
        chromeDriverService.start();

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        WebDriver webDriver = new ChromeDriver(chromeDriverService, chromeOptions);
        StdDraw.setCanvasSize(800, 600);
        try {
            webDriver.get("http://m.51tiangou.com");

            WebDriverWait webDriverWait = new WebDriverWait(webDriver, 5);
            webDriverWait.until(HttpRobot::documentReady);

            WebElement webElement = webDriver.findElement(By.xpath("/html"));
            String content = webElement.getAttribute("outerHTML");


            Document document = Jsoup.parse(content);

            document.select("img[src]").stream()
                    .map(e -> e.attr("src"))
                    .filter(s -> !s.contains("log"))
                    .forEach(HttpRobot::downloadAndShow);
        } finally {
            webDriver.quit();
            chromeDriverService.stop();

            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    super.run();
                    webDriver.quit();
                    chromeDriverService.stop();
                }
            });
        }
    }

    private static void downloadAndShow(String url) {
        if (!url.startsWith("http")) {
            url = "http:" + url;
        }

        try {
            BufferedImage image = StdDraw.getImageFast(new URL(url));
            if (image == null || image.getWidth() < 500 || image.getHeight() < 500) {
                return;
            }

            StdDraw.picture(0.2, 0.2, image);

            TimeUnit.MILLISECONDS.sleep(3);
        } catch (MalformedURLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean documentReady(WebDriver webDriver) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
        String ready = (String) jsExecutor.executeScript("return document.readyState");

        Boolean imageLoaded = (Boolean) jsExecutor.executeScript("return $('a').length > 2");

        return "complete".equalsIgnoreCase(ready) && imageLoaded;
    }
}
