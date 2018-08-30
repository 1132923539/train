package com.canway.train.service;

import com.canway.train.bean.ResultBean;
import com.canway.train.bean.vo.GroupCreatorInfo;
import com.canway.train.entity.GroupDO;

public interface GroupService extends BaseService<GroupDO> {


    ResultBean creatorGroup(GroupCreatorInfo groupCreatorInfo);

    Boolean deleteGroup(Long id);
}
