package com.diwayou.spring.controller;

import com.diwayou.spring.domain.CnbetaNewsVo;
import com.diwayou.spring.manager.CnbetaNewsManager;
import com.diwayou.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NewsController {

    @Autowired
    private CnbetaNewsManager cnbetaNewsManager;

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String index(@RequestParam(value = "size", defaultValue = "2") Integer size) {
        List<CnbetaNewsVo> news = cnbetaNewsManager.nextPage(size);

        return Json.nonNull().toJson(news);
    }
}
