package com.diwayou.spring.manager;

import com.diwayou.spring.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserManager {

    public void add(String name) {
        User user = new User();
        user.setName(name);
    }

    public User get(Integer id) {
        return new User();
    }
}
