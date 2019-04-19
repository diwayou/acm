package com.diwayou.web.http.robot;

import org.w3c.dom.html.HTMLDocument;

import java.io.Closeable;

public interface RobotDriver extends Closeable {

    Object executeScript(String script);

    void get(String url);

    HTMLDocument getDocument();
}
