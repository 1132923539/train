package com.canway.train.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class UserVo {
    private static final long serialVersionUID = 1L;


    private Long id;

    private String oldPassword;

    private String newPassword;

}
