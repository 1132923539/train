package com.canway.train.service;

import com.canway.train.bean.vo.GroupUserVO;
import com.canway.train.entity.GroupDO;
import com.canway.train.entity.GroupUserDO;

import java.util.List;

public interface GroupUserService extends BaseService<GroupUserDO> {


    Boolean creatorGroup(GroupUserDO groupUserDO);


    GroupUserVO selectGroupUserByGroupId(Long trainingId, GroupDO group);

    List<GroupUserVO> selectGroupUserByTrainingId(Long trainingId, List<GroupDO> groupDOList);
}
