package com.canway.train.bean.vo;

import com.canway.train.entity.GroupDO;
import com.canway.train.entity.UserDO;
import lombok.Data;

import java.util.List;

/**
 * 分组创建显示层实体类
 */
@Data
public class GroupCreatorInfo extends GroupDO {

    Long [] users;
}
