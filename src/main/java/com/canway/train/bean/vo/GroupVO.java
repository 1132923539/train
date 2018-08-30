package com.canway.train.bean.vo;

import lombok.Data;

@Data
public class GroupVO {

    private Long id;
    private String name;
    private Long trainingId;
    private String trainingName;
    private String users;

}
