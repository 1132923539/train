package com.canway.train.service.impl;

import com.canway.train.entity.UserDO;
import com.canway.train.mapper.UserMapper;
import com.canway.train.service.UserService;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper,UserDO> implements UserService {

}
