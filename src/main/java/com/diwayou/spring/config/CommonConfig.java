package com.diwayou.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gaopeng
 * @date 2020/8/27
 */
@Configuration
public class CommonConfig {

    @Bean
    public CrossAccessFilter crossAccessFilter() {
        return new CrossAccessFilter();
    }
}
