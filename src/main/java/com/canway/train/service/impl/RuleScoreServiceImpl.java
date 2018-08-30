package com.canway.train.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.canway.train.bean.RuleScoreVO;
import com.canway.train.entity.GroupDO;
import com.canway.train.entity.RuleScoreDO;
import com.canway.train.entity.UserDO;
import com.canway.train.mapper.RuleScoreMapper;
import com.canway.train.service.GroupService;
import com.canway.train.service.RuleScoreService;
import com.canway.train.service.UserService;
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

    @Autowired
    private UserService userService;

    @Override
    public List<RuleScoreVO> listAllRuleScore(Long trainingId) {
        List<RuleScoreVO> scoreVOList = new ArrayList<>();

        List<RuleScoreDO> ruleScores = this.selectList(new EntityWrapper<RuleScoreDO>().eq("training_id", trainingId));
        if (trainingId.equals(0)) {
            ruleScores = this.selectList(null);
        }

        List<GroupDO> groups = groupService.selectList(new EntityWrapper<GroupDO>().eq("training_id", trainingId));
        List<UserDO> users = userService.selectList(null);

        for (RuleScoreDO ruleScoreDO : ruleScores) {

            //给RuleScoreVO 赋值组名
            Stream<String> groupNames = groups.stream().filter(group -> ruleScoreDO.getGroupId().equals(group.getId())).map(g -> g.getName());
            String groupName = (String) groupNames.toArray()[0];
            RuleScoreVO ruleScoreVO = new RuleScoreVO();
            ruleScoreVO.setGroupName(groupName);

            //给RuleScoreVO 赋值用户字段
            Object[] userNames = users.stream().map(user -> user.getName()).toArray();
            String members = this.arrayToString(userNames);
            ruleScoreVO.setMembers(members);

            //给RuleScoreVO 赋值规则加分字段
            int score = sum(ruleScores, "score");
            ruleScoreVO.setScore(score);

            // 给RuleScoreVO 赋值规则扣分字段
            int points = sum(ruleScores, "points");
            ruleScoreVO.setPoints(points);

            // 给RuleScoreVO 赋值备注字段
            ruleScoreVO.setRemarks("这是一个字段");

            scoreVOList.add(ruleScoreVO);
        }

        return scoreVOList;
    }

    private String arrayToString(Object[] a) {
        if (a == null) {
            return "";
        }

        int iMax = a.length - 1;
        if (iMax == -1) {
            return "";
        }

        StringBuilder b = new StringBuilder();
        for (int i = 0; ; i++) {
            b.append(String.valueOf(a[i]));
            if (i == iMax) {
                return b.toString();
            }
            b.append(", ");
        }
    }

    private int sum(List<RuleScoreDO> ruleScores, String field) {
        int sum = 0;
        for (RuleScoreDO ruleScore : ruleScores) {
            Integer score = ruleScore.getScore();
            if (score == null) {
                score = 0;
            }
            switch (field) {
                case "points":
                    if (score < 0) {
                        sum += score;
                    }
                    break;
                default:
                    if (score > 0) {
                        sum += score;
                    }
            }
        }
        return sum;
    }
}
