package com.diwayou.acm.http;

import javafx.application.Platform;
import javafx.scene.web.WebEngine;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class TgouFxPageLoadReady implements PageLoadReady<AtomicReference<WebEngine>> {

    private AtomicBoolean result = new AtomicBoolean(false);

    @Override
    public Boolean apply(AtomicReference<WebEngine> engineHolder) {
        if (engineHolder.get() == null) {
            return Boolean.FALSE;
        }

        WebEngine webEngine = engineHolder.get();

        Platform.runLater(() -> {
            String ready = (String) webEngine.executeScript("document.readyState");

            Boolean imageLoaded = (Boolean) webEngine.executeScript("document.getElementsByTagName('a').length > 2;");
            System.out.println("ready=" + ready);
            System.out.println("imageLoaded=" + imageLoaded);

            result.set("complete".equalsIgnoreCase(ready) && imageLoaded);
        });

        System.out.println("load result=" + result.get());

        return result.get();
    }
}
