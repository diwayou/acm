package com.diwayou.web.config;

import java.util.prefs.Preferences;

public class AppConfig {
    private static final String IMAGE_KEY = "image.store";
    private static final String HTML_KEY = "html.store";
    private static final String DOC_KEY = "doc.store";

    private static final String NODE = "com/diwayou/browser";

    private static volatile boolean storeImage;

    private static volatile boolean storeHtml;

    private static volatile boolean storeDoc;

    static {
        storeImage = Preferences.userRoot().node(NODE).getBoolean(IMAGE_KEY, true);
        storeHtml = Preferences.userRoot().node(NODE).getBoolean(HTML_KEY, false);
        storeDoc = Preferences.userRoot().node(NODE).getBoolean(DOC_KEY, false);
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
