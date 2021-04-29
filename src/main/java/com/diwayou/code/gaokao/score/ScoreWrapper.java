package com.diwayou.code.gaokao.score;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaopeng 2021/5/6
 */
@Data
@NoArgsConstructor
public class ScoreWrapper {

    private String code;

    private String message;

    private DataResult data;

    @lombok.Data
    @NoArgsConstructor
    public static class DataResult {

        private String method;

        private String text;
    }
}
