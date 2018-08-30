package com.canway.train.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author eltons,  Date on 2018-08-29.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleScoreVO {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 成员名单
     */
    private String members;

    /**
     * 规则加分
     */
    private Integer score;

    /**
     * 规则扣分
     */
    private Integer points;

    /**
     * 备注
     */
    private String remarks;

}
