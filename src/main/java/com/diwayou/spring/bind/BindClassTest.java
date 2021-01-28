package com.diwayou.spring.bind;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;

import java.util.Properties;

/**
 * @author gaopeng 2021/1/28
 */
public class BindClassTest {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("user.name", "diwayou");
        properties.put("user.age", 10);

        Binder binder = new Binder(new MapConfigurationPropertySource(properties));
        User user = binder.bindOrCreate("user", User.class);

        System.out.println(user);
    }

    @Data
    @NoArgsConstructor
    private static class User {

        private String name;

        private int age;
    }
}
