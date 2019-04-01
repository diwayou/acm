package com.diwayou.acm.http;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

public class HttpRobot {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:\\opensource\\chromedriver.exe");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        WebDriver webDriver = new ChromeDriver(chromeOptions);
        webDriver.get("http://m.51tiangou.com");

        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 5);
        webDriverWait.until(HttpRobot::documentReady);

        WebElement webElement = webDriver.findElement(By.xpath("/html"));
        String content = webElement.getAttribute("outerHTML");

        Document document = Jsoup.parse(content);

        long imageCount = document.select("img").stream()
                .count();

        System.out.println(imageCount);
    }

    private static boolean documentReady(WebDriver webDriver) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor)webDriver;
        String ready = (String) jsExecutor.executeScript("return document.readyState");

        Boolean imageLoaded = (Boolean)jsExecutor.executeScript("return $('a').length > 2");

        return "complete".equalsIgnoreCase(ready) && imageLoaded;
    }
}
