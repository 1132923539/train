package com.canway.train.controller;

import com.baomidou.mybatisplus.mapper.Condition;
import com.canway.train.bean.ResultBean;
import com.canway.train.entity.UserDO;
import com.canway.train.service.UserService;
import com.canway.train.vo.UserVo;
import com.canway.train.util.MD5Util;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.canway.train.vo.UserVo;
import java.util.List;


@RequestMapping("/api/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping(value="/user")
    public ResultBean listAllUser(HttpServletRequest request){
        ResultBean resultBean = ResultBean.success();
        List<UserDO> userList = userService.selectList(Condition.create());
        resultBean.setData(userList);
        return resultBean;
    }


    /**
     * 新增用户
     * @param userDO
     * @return
     */
    @PostMapping(value = "/user",produces = "application/json;charset=UTF-8")
    public ResultBean insertUserDO(@RequestBody UserDO userDO){
        userDO.setPassword(MD5Util.convertMD5("123456"));
        boolean result = userService.insert(userDO);
        if(result){
            return ResultBean.success(userDO,"添加成功");
        }else{
            return ResultBean.fail(null,"添加不成功",HttpStatus.SERVICE_UNAVAILABLE);
        }
    }


    @PutMapping(value = "/user",produces = "application/json;charset=UTF-8")
    public ResultBean updateUserDO(@RequestBody UserDO userDO){
        boolean result = userService.updateById(userDO);
        if(result){
            return ResultBean.success(userDO,"修改成功");
        }else{
            return ResultBean.fail(null,"修改不成功",HttpStatus.SERVICE_UNAVAILABLE);
        }
    }


    @PutMapping(value = "/userPassword",produces = "application/json;charset=UTF-8")
    public ResultBean updatePassword(@RequestBody UserVo userVo){
        UserDO userDO = userService.selectById(userVo.getId());
        if (userDO == null) {
            return ResultBean.fail(null, "用户不存在", HttpStatus.SERVICE_UNAVAILABLE);
        }
        if (userVo.getOldPassword() == null || userVo.getNewPassword() == null || userVo.getId() == null) {
            return ResultBean.fail(null, "参数错误", HttpStatus.SERVICE_UNAVAILABLE);
        }
        if (!userVo.getOldPassword().equals(MD5Util.convertMD5(userDO.getPassword()))) {
            return ResultBean.fail(null, "原密码错误", HttpStatus.SERVICE_UNAVAILABLE);
        }

        userDO.setPassword(MD5Util.convertMD5(userVo.getNewPassword()));
        boolean result = userService.updateById(userDO);
        if(result){
            return ResultBean.success(userDO,"修改密码成功");
        }else{
            return ResultBean.fail(null,"修改密码不成功",HttpStatus.SERVICE_UNAVAILABLE);
        }
    }


    @DeleteMapping(value = "/delete/{id}")
    public ResultBean delete(@PathVariable Long id){
        boolean result = userService.deleteById(id);
        if(result){
            return ResultBean.success("删除成功");
        }else{
            return ResultBean.fail(null,"删除不成功",HttpStatus.SERVICE_UNAVAILABLE);
        }
    }








}
