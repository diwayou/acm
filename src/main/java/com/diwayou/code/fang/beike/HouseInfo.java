package com.diwayou.code.fang.beike;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author gaopeng 2021/2/20
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class HouseInfo {

    private String title;

    private String url;

    private String positionInfo;

    private String baseInfo;

    private String followInfo;

    private String totalPrice;

    private String unitPrice;
}
