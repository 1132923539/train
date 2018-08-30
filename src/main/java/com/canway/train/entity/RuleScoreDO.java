package com.canway.train.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author eltons,  Date on 2018-08-29.
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode()
@TableName("t_rule_score")
public class RuleScoreDO {

    private static final long serialVersionUID = 1L;

    /**
     * 规则分数表主键ID
     */
    private Long id;

    /**
     * 分组ID
     */
    private Long groupId;

    /**
     * 规则描述
     */
    private String rule;

    /**
     * 分数
     */
    private Integer score;

    /**
     * 加分扣分说明
     */
    private String remarks;
}
