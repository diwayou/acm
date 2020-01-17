package com.diwayou.spring.controller;

import com.diwayou.spring.manager.CnbetaNewsManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class NewsController {

    @Autowired
    private CnbetaNewsManager cnbetaNewsManager;

    @GetMapping
    public String index(Model model, @RequestParam(value = "size") Integer size) {
        model.addAttribute("news", cnbetaNewsManager.nextPage(size));

        return "index";
    }
}
