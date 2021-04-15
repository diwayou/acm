package com.diwayou.code.fang.search;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author gaopeng 2021/4/15
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BuildingInfo {

    /**
     * 楼id
     */
    private String id;

    /**
     * 几号楼
     */
    private String num;

    /**
     * 楼位置
     */
    private String location;

    /**
     * 总套数
     */
    private String houseCount;
}
