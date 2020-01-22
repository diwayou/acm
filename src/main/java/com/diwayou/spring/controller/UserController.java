package com.diwayou.spring.controller;

import com.alibaba.fastjson.JSON;
import com.diwayou.spring.domain.entity.User;
import com.diwayou.spring.manager.UserManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserManager userManager;

    @GetMapping(value = "/add")
    public String add(@RequestParam("name") String name) {
        userManager.add(name);

        return "Success";
    }

    @GetMapping(value = "/get")
    public String get(@RequestParam("id") Integer id) {
        User u = userManager.get(id);

        return JSON.toJSONString(u);
    }
}
