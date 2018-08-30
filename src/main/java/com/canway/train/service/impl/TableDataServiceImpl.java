package com.canway.train.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.canway.train.bean.RankVO;
import com.canway.train.entity.*;
import com.canway.train.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TableDataServiceImpl implements TableDataService {

    @Autowired
    private GroupService groupService;

    @Autowired
    private RuleScoreService ruleScoreService;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupUserService groupUserService;

    @Autowired
    private ScoreService scoreService;

    @Override
    public Map<Long,RankVO> getGroup(Long trainingId) {
        EntityWrapper ew = new EntityWrapper();
        ew.eq("training_id",trainingId);
        List<GroupDO> groupDOS = groupService.selectList(ew);
        Map<Long,RankVO> groupMaps = new HashMap<>();
        for (GroupDO groupDO:groupDOS){
            RankVO groupVO = new RankVO();
            groupVO.setId(groupDO.getId());
            groupVO.setName(groupDO.getName());
            groupVO.setUsers("");
            groupMaps.put(groupDO.getId(),groupVO);
        }
        List<GroupUserDO> groupUserDOS = groupUserService.selectList(ew);
        List<UserDO> userDOS = userService.selectList(new EntityWrapper<>());
        Map<Long,String> userMaps = new HashMap<>();
        for (UserDO userDO:userDOS){
            userMaps.put(userDO.getId(),userDO.getName());
        }
        for (GroupUserDO groupUserDO:groupUserDOS){
            if(groupMaps.containsKey(groupUserDO.getGroupId())&&userMaps.containsKey(groupUserDO.getUserId())){
                String name = userMaps.get(groupUserDO.getUserId());
                RankVO groupVO = groupMaps.get(groupUserDO.getGroupId());
                if(groupVO.getUsers().equals("")){
                    groupVO.setUsers(name);
                }
                else{
                    groupVO.setUsers(groupVO.getUsers()+","+name);
                }
                groupMaps.put(groupUserDO.getGroupId(),groupVO);
            }
        }
        return groupMaps;
    }

    @Override
    public List<RankVO>  getSpeechTotal(Map<Long, RankVO> groupVOMap, Long trainingId) {
        List<RankVO> rankVOS = new ArrayList<>();
        for(Map.Entry<Long,RankVO> entry:groupVOMap.entrySet()){
            RankVO rankVO = entry.getValue();
            EntityWrapper ew = new EntityWrapper();
            ew.eq("group_id",entry.getValue().getId());
            ew.eq("training_id",trainingId);
            List<ScoreDO> scoreDOS = scoreService.selectList(ew);
            Double sum = 0.0;
            for(ScoreDO scoreDO:scoreDOS){
                sum+=scoreDO.getScore();
            }
            rankVO.setSpeechScore(sum);
            if(scoreDOS.size()==0){
                rankVO.setSpeechAverage(0.0);
            }
            else{
                rankVO.setSpeechAverage(sum/scoreDOS.size());
            }
            rankVOS.add(rankVO);
        }
        Collections.sort(rankVOS, new Comparator<RankVO>() {
            @Override
            public int compare(RankVO o1, RankVO o2) {
                if(o1.getSpeechAverage()>o2.getSpeechAverage()){
                    return -1;
                }
                else if(o1.getSpeechAverage()<o2.getSpeechAverage()){
                    return 1;
                }
                else{
                    return 0;
                }
            }
        });
        Integer currentRank = 0;
        Double currentSpeechAverageScore = 0.0;
        for(Integer i = 0;i<rankVOS.size();i++){
            RankVO rankVO = rankVOS.get(i);
            if(i==0){
                currentRank = 1;
                rankVO.setSpeechRank(1);
            }
            else{
                if(Math.abs(currentSpeechAverageScore - rankVO.getSpeechAverage())<0.01){
                    rankVO.setSpeechRank(currentRank);
                }
                else{
                    currentRank = i+1;
                    rankVO.setSpeechRank(currentRank);
                }
            }
            rankVO.setSpeechTotal( 20.0-((currentRank-1)*2));
            currentSpeechAverageScore = rankVO.getSpeechAverage();
        }

        return rankVOS;
    }

    @Override
    public List<RankVO> solveRuleScore(List<RankVO> rankVOS,Long trainingId) {
        for(RankVO rankVO:rankVOS){
            EntityWrapper ew = new EntityWrapper();
            ew.eq("group_id",rankVO.getId());
            ew.eq("training_id",trainingId);
            List<RuleScoreDO> ruleScoreDOS = ruleScoreService.selectList(ew);
            Double addRuleScore = 0.0;
            Double deductRuleScore = 0.0;
            for(RuleScoreDO ruleScoreDO:ruleScoreDOS){
                if(ruleScoreDO.getScore()>0){
                    addRuleScore+=ruleScoreDO.getScore();
                }
                else{
                    deductRuleScore+=ruleScoreDO.getScore();
                }
            }
            rankVO.setAddRuleScore(addRuleScore);
            rankVO.setDeductRuleScore(deductRuleScore);
            Double total = rankVO.getSpeechTotal()+addRuleScore+deductRuleScore;
            rankVO.setTotalScore(total);
        }
        Collections.sort(rankVOS, new Comparator<RankVO>() {
            @Override
            public int compare(RankVO o1, RankVO o2) {
                if(o1.getTotalScore()>o2.getTotalScore()){
                    return -1;
                }
                else if(o1.getTotalScore()<o2.getTotalScore()){
                    return 1;
                }
                else{
                    return 0;
                }
            }
        });
        Integer currentRank = 0;
        Double currentScore = 0.0;
        for(Integer i = 0;i<rankVOS.size();i++){
            RankVO rankVO = rankVOS.get(i);
            if(i==0){
                currentRank = 1;
                rankVO.setRank(1);
            }
            else{
                if(Math.abs(currentScore-rankVO.getTotalScore())<0.01){
                    rankVO.setRank(currentRank);
                }
                else{
                    currentRank = i+1;
                    rankVO.setRank(currentRank);
                }
            }
            currentScore = rankVO.getTotalScore();
        }
        return rankVOS;
    }
}
