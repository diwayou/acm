package com.diwayou.web.config;

import java.util.prefs.Preferences;

public class AppConfig {
    private static final String IMAGE_KEY = "image.store";
    private static final String HTML_KEY = "html.store";
    private static final String DOC_KEY = "doc.store";
    private static final String IMAGE_LENGTH_KEY = "image.length";
    private static final String IMAGE_BROWSER_KEY = "image.browser";
    private static final String FX_RENDER_KEY = "render.fx";

    private static final String NODE = "com/diwayou/browser";

    private static volatile boolean storeImage;
    private static volatile boolean storeHtml;
    private static volatile boolean storeDoc;
    private static volatile boolean systemBrowser;
    private static volatile int imageLength;
    private static volatile boolean fxRender;

    static {
        storeImage = Preferences.userRoot().node(NODE).getBoolean(IMAGE_KEY, true);
        storeHtml = Preferences.userRoot().node(NODE).getBoolean(HTML_KEY, false);
        storeDoc = Preferences.userRoot().node(NODE).getBoolean(DOC_KEY, false);
        systemBrowser = Preferences.userRoot().node(NODE).getBoolean(IMAGE_BROWSER_KEY, true);
        imageLength = Preferences.userRoot().node(NODE).getInt(IMAGE_LENGTH_KEY, 20 * 1024);
        fxRender = Preferences.userRoot().node(NODE).getBoolean(FX_RENDER_KEY, false);
    }

    public static synchronized void toggleImage(boolean isStore) {
        storeImage = isStore;
        Preferences.userRoot().node(NODE).putBoolean(IMAGE_KEY, isStore);
    }

    public static synchronized void toggleHtml(boolean isStore) {
        storeHtml = isStore;
        Preferences.userRoot().node(NODE).putBoolean(HTML_KEY, isStore);
    }

    public static synchronized void toggleDoc(boolean isStore) {
        storeDoc = isStore;
        Preferences.userRoot().node(NODE).putBoolean(DOC_KEY, isStore);
    }

    public static synchronized void toggleSystemBrowser(boolean isSystemBrowser) {
        systemBrowser = isSystemBrowser;
        Preferences.userRoot().node(NODE).putBoolean(IMAGE_BROWSER_KEY, isSystemBrowser);
    }

    public static synchronized void setImageLengthLimit(int length) {
        imageLength = length;
        Preferences.userRoot().node(NODE).putInt(IMAGE_LENGTH_KEY, length);
    }

    public static synchronized void toggleFxRender(boolean isFxRender) {
        fxRender = isFxRender;
        Preferences.userRoot().node(NODE).putBoolean(FX_RENDER_KEY, isFxRender);
    }

    public static boolean isStoreImage() {
        return storeImage;
    }

    public static boolean isStoreHtml() {
        return storeHtml;
    }

    public static boolean isStoreDoc() {
        return storeDoc;
    }

    public static int getImageLength() {
        return imageLength;
    }

    public static boolean isSystemBrowser() {
        return systemBrowser;
    }

    public static boolean isFxRender() {
        return fxRender;
    }
}
