package com.canway.train.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Hanson
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode()
@TableName("t_kanban")
public class KanBanDO{

    private static final long serialVersionUID = 1L;


    private Long id;

    /**
     * 项目ID
     */
    private Long projectId;

    /**
     * 看板名称
     */
    private String kanBanName;


    /**
     * 排序号
     */
    private Integer kanBanOrder;

}