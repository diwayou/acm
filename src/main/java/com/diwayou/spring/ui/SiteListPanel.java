package com.diwayou.spring.ui;

import com.diwayou.spring.parser.PageParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;

@Component
public class SiteListPanel {

    private JList<String> buttons;
    private PageParser pageParser;

    public SiteListPanel(@Autowired PageParser pageParser) {
        this.pageParser = pageParser;
        this.buttons = new JList<>(new String[]{"a", "b", "c"});
    }

    @PostConstruct
    public void init() {
        System.out.println("SiteListPanel init success");
    }

    public JList<String> getButtons() {
        return buttons;
    }
}
