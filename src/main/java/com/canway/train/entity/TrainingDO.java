package com.canway.train.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author eltons,  Date on 2018-08-29.
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode()
@TableName("t_training")
public class TrainingDO {
    private static final long serialVersionUID = 1L;

    /**
     * 培训表主键ID
     */
    private Long id;

    /**
     * 培训主题
     */
    private String subject;

    /**
     * 培训说明
     */
    private String remarks;

    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 培训开关
     */
    private Integer isOpen;
}
