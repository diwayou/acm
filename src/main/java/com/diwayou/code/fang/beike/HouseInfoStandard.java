package com.diwayou.code.fang.beike;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author gaopeng 2021/2/20
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class HouseInfoStandard {

    @ExcelProperty("标题")
    private String title;

    @ExcelProperty("url地址")
    private String url;

    @ExcelProperty("位置信息")
    private String positionInfo;

    @ExcelProperty("基本信息")
    private String baseInfo;

    @ExcelProperty("关注人数")
    private int followNum;

    @ExcelProperty("发布时间")
    private String publishTime;

    @ExcelProperty("总价")
    private double totalPrice;

    @ExcelProperty("单价")
    private int unitPrice;
}
