package com.canway.train.service;

import com.canway.train.bean.vo.ScoreVO;
import com.canway.train.entity.ScoreDO;

import java.util.List;

public interface ScoreService extends BaseService<ScoreDO> {


    List<ScoreVO> selectScoreVOList(Long trainingId);
}
