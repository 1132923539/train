package com.canway.train.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户分组关联表
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode()
@TableName("t_group_user")
public class GroupUserDO {
    private Long id; //  '主键',
    private Long trainingId; //'培训ID',
    private Long userId; //'用户ID',
    private Long groupId; // '分组ID',
}
