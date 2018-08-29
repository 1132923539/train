package com.canway.train.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.canway.train.bean.RuleScoreVO;
import com.canway.train.entity.RuleScoreDO;
import com.canway.train.mapper.RuleScoreMapper;
import com.canway.train.service.RuleScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author eltons,  Date on 2018-08-29.
 */
@Service
public class RuleScoreServiceImpl extends BaseServiceImpl<RuleScoreMapper, RuleScoreDO> implements RuleScoreService {


    @Override
    public List<RuleScoreVO> listAllRuleScore(Long trainingId) {
//        this.selectPage(Page < >)
        return null;
    }

    @Override
    public void updateRuleScore(Long trainingId, Long ruleScoreId) {

    }
}
