package com.canway.train.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.canway.train.bean.vo.GroupScoreVO;
import com.canway.train.bean.vo.ScoreVO;
import com.canway.train.entity.GroupDO;
import com.canway.train.entity.GroupUserDO;
import com.canway.train.entity.ScoreDO;
import com.canway.train.entity.UserDO;
import com.canway.train.mapper.ScoreMapper;
import com.canway.train.service.GroupService;
import com.canway.train.service.GroupUserService;
import com.canway.train.service.ScoreService;
import com.canway.train.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ScoreServiceImpl extends BaseServiceImpl<ScoreMapper,ScoreDO> implements ScoreService {

    @Autowired
    private UserService userService;

    @Autowired
    private GroupUserService groupUserService;

    @Autowired
    private GroupService groupService;


    @Override
    public List<ScoreVO> selectScoreVOList(Long trainingId) {

        List<ScoreVO> scoreVOList = new ArrayList<ScoreVO>();
        //获取培训下所有能评分的分组信息
        Map<Long,String> groupMap = this.getGroup(trainingId);

        //获取所有用户
        List<UserDO> userDOList = userService.selectList(new EntityWrapper<UserDO>());
        if (userDOList != null && userDOList.size() >0){
            //轮询所有用户计算出
            for (UserDO userDO:userDOList) {
                Map<Long,String> map = new HashMap<Long,String>();
                map.putAll(groupMap);
                scoreVOList.add(this.getScoreVo(trainingId,userDO,map));
            }
        }
        return scoreVOList;
    }


    //获取培训id下所有可以评分的分组名称和id（map）
    public Map<Long,String> getGroup(Long trainingId){
        Map<Long,String> map = new HashMap<Long,String>();
        //获取培训下所有分组列表
        List<GroupDO> groupDOList = groupService.selectList(new EntityWrapper<GroupDO>()
                .eq("training_id",trainingId));
        if (groupDOList != null && groupDOList.size() >0){
            for (GroupDO groupDO:groupDOList) {
                map.put(groupDO.getId(),groupDO.getName());
            }
        }
        return map;
    }

    /**
     * 封装ScoreVO
     * @param trainingId
     * @param userDO
     * @param groupMap
     * @return
     */
    public ScoreVO getScoreVo(Long trainingId,UserDO userDO,Map<Long,String> groupMap){
        ScoreVO scoreVO = new ScoreVO();
        //获取用户所在的分组
        List<GroupUserDO> groupUserDOlist = groupUserService.selectList(new EntityWrapper<GroupUserDO>()
                .eq("training_id",trainingId).eq("user_id",userDO.getId()));
        if (groupUserDOlist != null && groupUserDOlist.size() >0){
            scoreVO.setGroupId(groupUserDOlist.get(0).getGroupId());
            scoreVO.setGroupName(groupMap.get(groupUserDOlist.get(0).getGroupId()));
            groupMap.remove(groupUserDOlist.get(0).getGroupId());
        }

        List<ScoreVO.Score> scoreList = new ArrayList<ScoreVO.Score>();

        for (Long groupId : groupMap.keySet()) {
            com.canway.train.bean.vo.ScoreVO.Score score= new com.canway.train.bean.vo.ScoreVO.Score();
            //获取用户对对应组的评分
            List<ScoreDO> scoreDOList = this.selectList(new EntityWrapper<ScoreDO>()
                    .eq("user_id",userDO.getId())
                    .eq("group_id",groupId)
                    .eq("training_id",trainingId));

            if (scoreDOList != null && scoreDOList.size() >0){
                score.setId(scoreDOList.get(0).getId());
                score.setScore(scoreDOList.get(0).getScore());
            }

            score.setGroupId(groupId);
            score.setGroupName(groupMap.get(groupId));
            scoreList.add(score);
        }
        scoreVO.setTrainingId(trainingId);
        scoreVO.setUserId(userDO.getId());
        scoreVO.setUserName(userDO.getName());
        scoreVO.setScoreList(scoreList);
        return scoreVO;
    }

    /**
     * //获取用户可以评分的分组信息
     * @param trainingId 培训id
     * @param userId 用户所在的组
     * @return
     */
    @Override
    public List<GroupScoreVO> selectGroupList(Long trainingId,Long userId) {
        List<GroupScoreVO> groupScoreVOList = new ArrayList<GroupScoreVO>();

        //获取用户所在的分组
        List<GroupUserDO> groupUserDOList = groupUserService.selectList(new EntityWrapper<GroupUserDO>()
                .eq("training_id",trainingId).eq("user_id",userId));

        List<GroupDO> groupDOList = new ArrayList<GroupDO>();

        //判断用户所在的分组和过滤不能评分的分组
        if (groupUserDOList != null && groupUserDOList.size() >0){
            groupDOList.addAll(groupService.selectList(new EntityWrapper<GroupDO>()
                    .eq("training_id",trainingId)
                    .eq("is_open",1)
                    .ne("id",groupUserDOList.get(0).getGroupId())));
        }else {
            groupDOList.addAll(groupService.selectList(new EntityWrapper<GroupDO>()
                    .eq("training_id",trainingId)
                    .eq("is_open",1)));
        }

        for (GroupDO groupDO:groupDOList) {
            GroupScoreVO groupScoreVO = new GroupScoreVO();
            BeanUtils.copyProperties(groupDO,groupScoreVO);

            //获取分组的评分
            List<ScoreDO> list = this.selectList(new EntityWrapper<ScoreDO>().eq("training_id",trainingId)
                    .eq("user_id",userId).eq("group_id",groupDO.getId()));

            if (list != null && list.size() >0 ){
                groupScoreVO.setScore(list.get(0).getScore());
                groupScoreVO.setScoreId(list.get(0).getId());
            }

            groupScoreVOList.add(groupScoreVO);
        }

        return groupScoreVOList;
    }
}
