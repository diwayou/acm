package com.diwayou.spring.ioc;

import org.springframework.stereotype.Service;

/**
 * @author gaopeng
 * @date 2020/9/17
 */
@Service
public class StoreService {

    public Store get(int id) {
        return new Store(1, "test");
    }
}
