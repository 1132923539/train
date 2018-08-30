package com.canway.train.service;

import com.canway.train.bean.RuleScoreVO;
import com.canway.train.entity.RuleScoreDO;

import java.util.List;

/**
 * @author eltons,  Date on 2018-08-29.
 */
public interface RuleScoreService extends BaseService<RuleScoreDO> {

    /**
     * 查出所有规则得分列表
     *
     * @param trainingId
     * @return
     */
    List<RuleScoreVO> listAllRuleScore(Long trainingId);


}
