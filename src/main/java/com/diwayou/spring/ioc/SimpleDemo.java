package com.diwayou.spring.ioc;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author gaopeng
 * @date 2020/9/17
 */
public class SimpleDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext("com.diwayou.spring.ioc");

        StoreManager storeManager = ctx.getBean(StoreManager.class);

        System.out.println(storeManager.get(1));
    }
}
