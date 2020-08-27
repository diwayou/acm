package com.diwayou.spring.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/sudoku")
public class SudokuController {

    @GetMapping(path = "/rooms", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> index() {
        return Arrays.asList("1", "2", "3");
    }
}
