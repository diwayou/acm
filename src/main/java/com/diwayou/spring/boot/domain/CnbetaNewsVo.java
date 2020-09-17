package com.diwayou.spring.boot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CnbetaNewsVo {
    private String title;

    private String meta;

    private String body;

    private String url;
}
