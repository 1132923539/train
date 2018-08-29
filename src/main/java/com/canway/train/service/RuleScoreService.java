package com.canway.train.service;

import com.canway.train.bean.RuleScoreVO;
import com.canway.train.entity.RuleScoreDO;

import java.util.List;

/**
 * @author eltons,  Date on 2018-08-29.
 */
public interface RuleScoreService extends BaseService<RuleScoreDO> {

    List<RuleScoreVO> listAllRuleScore(Long trainingId);

    void updateRuleScore(Long trainingId, Long ruleScoreId);
}
