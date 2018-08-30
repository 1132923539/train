package com.canway.train.bean;

import com.canway.train.entity.GroupDO;
import lombok.Data;

@Data
public class RankVO {

    private Long id;

    private String name;

    private String users;

    private Integer rank;

    private Double totalScore;

    private Double speechScore;

    private Double addRuleScore;

    private Double deductRuleScore;

    private Integer speechRank;

    private Double speechTotal;

    private Double speechAverage;
}
