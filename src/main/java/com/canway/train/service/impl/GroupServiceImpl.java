package com.canway.train.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.canway.train.bean.ResultBean;
import com.canway.train.bean.vo.GroupCreatorInfo;
import com.canway.train.bean.vo.GroupScoreVO;
import com.canway.train.bean.vo.GroupVO;
import com.canway.train.entity.GroupDO;
import com.canway.train.entity.GroupUserDO;
import com.canway.train.entity.TrainingDO;
import com.canway.train.entity.UserDO;
import com.canway.train.mapper.GroupMapper;
import com.canway.train.service.GroupService;
import com.canway.train.service.GroupUserService;
import com.canway.train.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class GroupServiceImpl extends BaseServiceImpl<GroupMapper,GroupDO> implements GroupService {

    @Autowired
    private GroupUserService groupUserService;

    @Autowired
    private UserService userService;


    @Override
    @Transactional
    public ResultBean creatorGroup(GroupCreatorInfo groupCreatorInfo) {
        GroupDO groupDO = new GroupDO();
        BeanUtils.copyProperties(groupCreatorInfo,groupDO);
        Boolean result = this.insert(groupDO);
        if (result){
            groupCreatorInfo.setId(groupDO.getId());
            Long [] users = groupCreatorInfo.getUsers();
            if (users != null && users.length >0){
                for (Long userId:users) {
                    GroupUserDO groupUserDO = new GroupUserDO();
                    groupUserDO.setTrainingId(groupDO.getTrainingId());
                    groupUserDO.setGroupId(groupDO.getId());
                    groupUserDO.setUserId(userId);
                    groupUserService.creatorGroup(groupUserDO);
                }
            }
            return ResultBean.success(groupCreatorInfo,"分组创建成功");
        }else{
            return ResultBean.fail(null,"创建分组失败。",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 删除分组和用户分组关联表的信息
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Boolean deleteGroup(Long id) {
        Boolean result = this.deleteById(id);
        if (result){
            groupUserService.delete(new EntityWrapper<GroupUserDO>().eq("group_id",id));
        }
        return result;
    }

    /**
     * 获取所有分组信息
     * @param trainingDOList
     * @return
     */
    @Override
    public List<GroupVO> selectGroupList(List<TrainingDO> trainingDOList) {
        List<GroupVO> groupVOList = new ArrayList<>();
        if(trainingDOList != null && trainingDOList.size() > 0){
            for (TrainingDO trainingDO : trainingDOList){
                List<GroupDO> groupDOList = this.selectList(new EntityWrapper<GroupDO>()
                        .eq("training_id", trainingDO.getId()));
                if (groupDOList != null && groupDOList.size() >0){
                    for (GroupDO groupDO:groupDOList) {
                        GroupVO groupVO = new GroupVO();
                        groupVO.setId(groupDO.getId());
                        groupVO.setName(groupDO.getName());
                        groupVO.setTrainingId(trainingDO.getId());
                        groupVO.setTrainingName(trainingDO.getSubject());

                        //封装用户信息
                        List<GroupUserDO> groupUserDOList = groupUserService.selectList(new EntityWrapper<GroupUserDO>()
                                .eq("training_id",trainingDO.getId()).eq("group_id",groupDO.getId()));
                        if (groupUserDOList != null && groupUserDOList.size() > 0){
                            StringBuffer users = new StringBuffer();
                            for (GroupUserDO groupUserDO :groupUserDOList){
                                UserDO userDO = userService.selectById(groupUserDO.getUserId());
                                if (userDO != null && userDO.getName() != null){
                                    users.append(userDO.getName() + ",");
                                }
                            }
                            if (users.length() > 0){
                                groupVO.setUsers(users.substring(0,users.length()-1));
                            }
                        }


                        groupVOList.add(groupVO);
                    }
                }

            }
        }

        return groupVOList;
    }


}
