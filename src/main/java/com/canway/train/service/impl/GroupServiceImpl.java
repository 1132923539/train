package com.canway.train.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.canway.train.bean.ResultBean;
import com.canway.train.bean.vo.GroupCreatorInfo;
import com.canway.train.entity.GroupDO;
import com.canway.train.entity.GroupUserDO;
import com.canway.train.entity.UserDO;
import com.canway.train.mapper.GroupMapper;
import com.canway.train.service.GroupService;
import com.canway.train.service.GroupUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GroupServiceImpl extends BaseServiceImpl<GroupMapper,GroupDO> implements GroupService {

    @Autowired
    private GroupUserService groupUserService;

    @Override
    public ResultBean creatorGroup(GroupCreatorInfo groupCreatorInfo) {
        GroupDO groupDO = new GroupDO();
        BeanUtils.copyProperties(groupCreatorInfo,groupDO);
        Boolean result = this.insert(groupDO);
        if (result){
            groupCreatorInfo.setId(groupDO.getId());
            List<UserDO> userDOList = groupCreatorInfo.getUsers();
            if (userDOList != null && userDOList.size()>0){
                for (UserDO userDO:userDOList) {
                    GroupUserDO groupUserDO = new GroupUserDO();
                    groupUserDO.setTrainingId(groupDO.getTrainingId());
                    groupUserDO.setGroupId(groupDO.getId());
                    groupUserDO.setUserId(userDO.getId());
                    groupUserService.insert(groupUserDO);
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
    public Boolean deleteGroup(Long id) {
        Boolean result = this.deleteById(id);
        if (result){
            groupUserService.delete(new EntityWrapper<GroupUserDO>().eq("group_id",id));
        }
        return result;
    }
}
