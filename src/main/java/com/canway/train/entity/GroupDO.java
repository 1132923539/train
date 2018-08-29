package com.canway.train.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 分组
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode()
@TableName("t_group")
public class GroupDO {

    private Long id; //'主键',
    private Long trainingId; // '培训ID',
    private String name; // '组名',
    private int isOpen; // '评分开关',
}
