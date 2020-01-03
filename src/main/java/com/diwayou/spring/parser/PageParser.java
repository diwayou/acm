package com.diwayou.spring.parser;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PageParser {

    @PostConstruct
    public void init() {
        System.out.println("PageParser init success");
    }
}
