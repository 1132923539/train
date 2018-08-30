package com.canway.train.bean.vo;

import com.canway.train.entity.ScoreDO;
import lombok.Data;

import java.util.List;

@Data
public class ScoreVO {
    private Long trainingId; //培训id
    private Long userId;//用户id
    private String userName; //用户名
    private Long groupId; //所在的分组id
    private String groupName; //所在的分组名称

    //评分列表
    private List<Score> scoreList;

    @Data
    public static class Score{
        private Long id; //'主键',
        private Long groupId; // '目标组ID',
        private String groupName;//目标分组名称
        private int score; // '分数',
    }
}
