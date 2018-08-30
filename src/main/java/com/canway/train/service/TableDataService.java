package com.canway.train.service;

import com.canway.train.bean.RankVO;

import java.util.List;
import java.util.Map;

public interface TableDataService  {

    public Map<Long,RankVO> getGroup(Long trainingId);

    public List<RankVO> getSpeechTotal(Map<Long,RankVO> groupVOMap, Long trainingId);

    public List<RankVO> solveRuleScore(List<RankVO> rankVOS,Long trainingId);

}
