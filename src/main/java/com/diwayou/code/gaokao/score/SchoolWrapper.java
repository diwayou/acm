package com.diwayou.code.gaokao.score;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaopeng 2021/4/29
 */
@Data
@NoArgsConstructor
public class SchoolWrapper {

    private String code;

    private String message;

    private String md5;

    private School data;
}
