package com.diwayou.spring.manager;

import com.diwayou.spring.dao.UserMapper;
import com.diwayou.spring.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserManager {

    @Autowired
    private UserMapper userMapper;

    public void add(String name) {
        User user = new User();
        user.setName(name);

        userMapper.insert(user);
    }

    public User get(Integer id) {
        return userMapper.getById(id);
    }
}
