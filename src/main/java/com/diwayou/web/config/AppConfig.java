package com.diwayou.web.config;

import java.util.prefs.Preferences;

public class AppConfig {
    private static final String IMAGE_KEY = "com.diwayou.browser.image.store";
    private static final String HTML_KEY = "com.diwayou.browser.html.store";
    private static final String DOC_KEY = "com.diwayou.browser.doc.store";

    private static volatile boolean storeImage;

    private static volatile boolean storeHtml;

    private static volatile boolean storeDoc;

    static {
        storeImage = Preferences.userRoot().getBoolean(IMAGE_KEY, true);
        storeHtml = Preferences.userRoot().getBoolean(HTML_KEY, false);
        storeDoc = Preferences.userRoot().getBoolean(DOC_KEY, false);
    }

    public static synchronized void toggleImage(boolean isStore) {
        storeImage = isStore;
        Preferences.userRoot().putBoolean(IMAGE_KEY, isStore);
    }

    public static synchronized void toggleHtml(boolean isStore) {
        storeHtml = isStore;
        Preferences.userRoot().putBoolean(HTML_KEY, isStore);
    }

    public static synchronized void toggleDoc(boolean isStore) {
        storeDoc = isStore;
        Preferences.userRoot().putBoolean(DOC_KEY, isStore);
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
}
