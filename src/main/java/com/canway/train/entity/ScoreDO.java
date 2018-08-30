package com.canway.train.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 评分
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode()
@TableName("t_score")
public class ScoreDO {

    private Long id; //'主键',
    private Long trainingId; // '培训ID',
    private Long userId; // '用户ID',
    private Long groupId; // '目标组ID',
    private int score; // '分数',
}
