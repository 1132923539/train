package com.canway.train.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.canway.train.bean.vo.ScoreVO;
import com.canway.train.entity.GroupUserDO;
import com.canway.train.entity.ScoreDO;
import com.canway.train.entity.UserDO;
import com.canway.train.mapper.ScoreMapper;
import com.canway.train.service.GroupUserService;
import com.canway.train.service.ScoreService;
import com.canway.train.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ScoreServiceImpl extends BaseServiceImpl<ScoreMapper,ScoreDO> implements ScoreService {

    @Autowired
    private UserService userService;

    @Autowired
    private GroupUserService groupUserService;


    @Override
    public List<ScoreVO> selectScoreVOList(Long trainingId) {
        List<ScoreVO> scoreVOList = new ArrayList<ScoreVO>();
        //获取所有用户
        List<UserDO> userDOList = userService.selectList(new EntityWrapper<UserDO>());
        if (userDOList != null && userDOList.size() >0){
            for (UserDO userDO:userDOList) {


            }
        }


        return scoreVOList;
    }
}
