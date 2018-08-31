package com.canway.train.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.canway.train.bean.RuleScoreVO;
import com.canway.train.entity.GroupDO;
import com.canway.train.entity.GroupUserDO;
import com.canway.train.entity.RuleScoreDO;
import com.canway.train.entity.UserDO;
import com.canway.train.mapper.RuleScoreMapper;
import com.canway.train.service.GroupService;
import com.canway.train.service.GroupUserService;
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

    @Autowired
    private GroupUserService groupUserService;

    @Override
    public List<RuleScoreVO> listAllRuleScore(Long trainingId) {
        List<RuleScoreVO> scoreVOList = new ArrayList<>();

        List<RuleScoreDO> ruleScores = this.selectList(new EntityWrapper<RuleScoreDO>().eq("training_id", trainingId));
        if (trainingId == 0) {
            ruleScores = this.selectList(null);
        }

        List<GroupDO> groups = groupService.selectList(new EntityWrapper<GroupDO>().eq("training_id", trainingId));
        List<UserDO> users = userService.selectList(null);


        for (RuleScoreDO ruleScoreDO : ruleScores) {

            //给RuleScoreVO 赋值组名
            Stream<String> groupNamesStream = groups.stream().filter(group -> ruleScoreDO.getGroupId().equals(group.getId())).map(g -> g.getName());
            Object[] groupNames = groupNamesStream.toArray();
            String groupName = null;
            if (groupNames.length != 0) {
                groupName = (String) groupNames[0];
            }
            RuleScoreVO ruleScoreVO = new RuleScoreVO();
            ruleScoreVO.setGroupName(groupName);

            //给RuleScoreVO 赋值用户字段
            Long groupId = ruleScoreDO.getGroupId();
            List<GroupUserDO> groupUsers = groupUserService.selectList(new EntityWrapper<GroupUserDO>().eq("group_id", groupId).eq("training_id", trainingId));
            List<String> names = new ArrayList<String>();
            for (GroupUserDO groupUserDO : groupUsers) {
                Long userId = groupUserDO.getUserId();
                UserDO userDO = userService.selectById(userId);
                names.add(userDO.getName());
            }

            String members = this.arrayToString(names.toArray());
            ruleScoreVO.setMembers(members);

            //给RuleScoreVO 赋值规则加分字段
            Integer score = ruleScoreDO.getScore();
            if (score >= 0) {
                ruleScoreVO.setScore(score);
            }

            // 给RuleScoreVO 赋值规则扣分字段
            if (score < 0) {
                ruleScoreVO.setPoints(score);
            }

            // 给RuleScoreVO 赋值备注字段
            ruleScoreVO.setRemarks(ruleScoreDO.getRemarks());

            ruleScoreVO.setId(ruleScoreDO.getId());
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
}
