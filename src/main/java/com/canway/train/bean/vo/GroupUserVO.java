package com.canway.train.bean.vo;

import com.canway.train.entity.UserDO;
import lombok.Data;

import java.util.List;

/**
 * 分组显示用户信息
 */
@Data
public class GroupUserVO {

    private Long groupId;

    private String groupName;

    private List<UserDO> userlist;

}
