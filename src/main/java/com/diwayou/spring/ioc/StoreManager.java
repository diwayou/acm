package com.diwayou.spring.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author gaopeng
 * @date 2020/9/17
 */
@Component
public class StoreManager {

    @Autowired
    private StoreService storeService;

    public Store get(int id) {
        return storeService.get(id);
    }
}
