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
@TableName("t_user")
public class UserDO {

    private static final long serialVersionUID = 1L;


    private Long id;

    /**
     * 账号
     */
    private String account;

    /**
     * 名称
     */
    private String name;

    /**
     * 密码
     */
    private String password;


    /**
     * 是否管理员
     */
    private Integer isAdmin;


    /**
     * 是否锁定
     */
    private Integer isLocked;

}