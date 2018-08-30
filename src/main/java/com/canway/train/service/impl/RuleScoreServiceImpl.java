package com.canway.train.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.canway.train.bean.RuleScoreVO;
import com.canway.train.entity.GroupDO;
import com.canway.train.entity.RuleScoreDO;
import com.canway.train.mapper.RuleScoreMapper;
import com.canway.train.service.GroupService;
import com.canway.train.service.RuleScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author eltons,  Date on 2018-08-29.
 */
@Service
public class RuleScoreServiceImpl extends BaseServiceImpl<RuleScoreMapper, RuleScoreDO> implements RuleScoreService {

    @Autowired
    private GroupService groupService;

    @Override
    public List<RuleScoreVO> listAllRuleScore(Long trainingId) {
        List<RuleScoreVO> scoreVOList = new ArrayList<>();

        List<RuleScoreDO> ruleScores = this.selectList(null);
        List<GroupDO> groups = groupService.selectList(null);

        //给RuleScoreVO 赋值组名
        ruleScores.forEach(ruleScoreDO -> {
            Stream<String> groupNames = groups.stream().filter(group -> ruleScoreDO.getId().equals(group.getId())).map(g -> g.getName());
            String groupName = (String) groupNames.toArray()[0];

            RuleScoreVO ruleScoreVO = new RuleScoreVO();
            ruleScoreVO.setGroupName(groupName);
            scoreVOList.add(ruleScoreVO);
        });


        this.selectCount(new EntityWrapper<RuleScoreDO>().eq("training_id", trainingId).groupBy("groep_id"));
        return null;
    }


}
