package com.diwayou.code.gaokao.score;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaopeng 2021/5/6
 */
@Data
@NoArgsConstructor
public class Score {

    @JsonProperty("school_id")
    private Integer schoolId;

    @JsonProperty("spname")
    private String name;

    @JsonProperty("local_batch_name")
    private String batchName;

    @JsonProperty("local_type_name")
    private String typeName;

    @JsonProperty("min")
    private Integer min;

    @JsonProperty("min_section")
    private Integer rank;

    @JsonProperty("year")
    private Integer year;
}
