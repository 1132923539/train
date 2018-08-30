package com.canway.train.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.canway.train.bean.vo.GroupUserVO;
import com.canway.train.entity.GroupDO;
import com.canway.train.entity.GroupUserDO;
import com.canway.train.entity.UserDO;
import com.canway.train.mapper.GroupUserMapper;
import com.canway.train.service.GroupUserService;
import com.canway.train.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class GroupUserServiceImpl extends BaseServiceImpl<GroupUserMapper,GroupUserDO> implements GroupUserService {

    @Autowired
    private UserService userService;

    @Transactional
    @Override
    public Boolean creatorGroup(GroupUserDO groupUserDO) {
        List<GroupUserDO> groupUserDOList = this.selectList(new EntityWrapper<GroupUserDO>()
                .eq("training_id",groupUserDO.getTrainingId()).eq("user_id",groupUserDO.getUserId()));
        if (groupUserDOList != null && groupUserDOList.size() >0){
            this.deleteById(groupUserDOList.get(0).getId());
        }
        return this.insert(groupUserDO);
    }

    /**
     * 根据培训id和分组id，获取用户信息
     * @param trainingId
     * @param group
     * @return
     */
    @Override
    public GroupUserVO selectGroupUserByGroupId(Long trainingId,GroupDO group) {
        GroupUserVO groupUserVO = new GroupUserVO();
        List<UserDO> userDOList = new ArrayList<UserDO>();

        List<GroupUserDO> list = this.selectList(new EntityWrapper<GroupUserDO>()
                .eq("training_id",trainingId).eq("group_id",group.getId()));
        if (list !=null && list.size()>0){
            for (GroupUserDO groupUserDO:list) {
                userDOList.add(userService.selectById(groupUserDO.getUserId()));
            }
        }
        groupUserVO.setGroupId(group.getId());
        groupUserVO.setGroupName(group.getName());
        groupUserVO.setUserlist(userDOList);
        return groupUserVO;
    }

    /**
     * 根据培训id分组获取用户信息
     */
    @Override
    public List<GroupUserVO> selectGroupUserByTrainingId(Long trainingId, List<GroupDO> groupDOList) {
        List<GroupUserVO> groupUserVOList = new ArrayList<GroupUserVO>();

        if (groupDOList != null && groupDOList.size() >0){
            for (GroupDO groupDO:groupDOList) {
                groupUserVOList.add(selectGroupUserByGroupId(trainingId,groupDO));
            }
        }
        return groupUserVOList;
    }
}
