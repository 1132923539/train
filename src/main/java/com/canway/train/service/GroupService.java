package com.canway.train.service;

import com.canway.train.bean.ResultBean;
import com.canway.train.bean.vo.GroupCreatorInfo;
import com.canway.train.bean.vo.GroupVO;
import com.canway.train.entity.GroupDO;
import com.canway.train.entity.TrainingDO;

import java.util.List;

public interface GroupService extends BaseService<GroupDO> {


    ResultBean creatorGroup(GroupCreatorInfo groupCreatorInfo);

    Boolean deleteGroup(Long id);

    List<GroupVO> selectGroupList(List<TrainingDO> trainingDOList);
}
