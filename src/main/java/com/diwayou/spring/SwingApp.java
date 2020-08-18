package com.diwayou.spring;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SwingApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SwingApp.class)
                .headless(false).run(args);
    }
}
