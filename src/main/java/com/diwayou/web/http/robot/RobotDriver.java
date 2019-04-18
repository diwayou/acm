package com.diwayou.web.http.robot;

import org.w3c.dom.html.HTMLDocument;

public interface RobotDriver extends AutoCloseable {

    Object executeScript(String script);

    void get(String url);

    HTMLDocument getDocument();
}
