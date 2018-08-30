package com.canway.train.bean;

import lombok.Data;

@Data
public class TotalRanking {

    private Integer rank;

    private Float total;

    private Integer speechScore;

    private Integer addScore;

    private Integer deductScore;

    private String groupName;

    private String people;

}
