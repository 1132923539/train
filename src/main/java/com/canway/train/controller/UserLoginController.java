package com.canway.train.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.canway.train.bean.ResultBean;
import  com.canway.train.entity.UserDO;
import com.canway.train.service.UserService;
import com.canway.train.vo.LoginUser;
import com.canway.train.vo.UserVo;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Hanson
 */

@RequestMapping("/api/userLogin")
@RestController
public class UserLoginController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @return
     * @throws Exception
     */
    @PostMapping("/login")
    public ResultBean userLogin(@RequestBody LoginUser loginUser) throws Exception{
        ResultBean resultBean = ResultBean.success();
        JSONObject jsonObject = new JSONObject();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginUser.getUserName(),loginUser.getPassword());
        try {
            subject.login(token);
            Session session = SecurityUtils.getSubject().getSession();
            jsonObject.put("token", subject.getSession().getId());
            jsonObject.put("userId", session.getAttribute("userId"));
            jsonObject.put("userName", session.getAttribute("userName"));
            jsonObject.put("userSession", session.getAttribute("userSession"));
        } catch (IncorrectCredentialsException e) {
            jsonObject.put("msg", "密码错误");
            resultBean.setSuccess(false);
            resultBean.setMsg("密码错误");
            resultBean.setData(jsonObject);
        } catch (LockedAccountException e) {
            jsonObject.put("msg", "登录失败，该用户已被锁定");
            resultBean.setSuccess(false);
            resultBean.setMsg("用户被锁定");
            resultBean.setData(jsonObject);
        } catch (AuthenticationException e) {
            jsonObject.put("msg", "该用户不存在");
            resultBean.setSuccess(false);
            resultBean.setMsg("密码错误");
            resultBean.setData(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        resultBean.setData(jsonObject);
        return resultBean;
    }

    @RequestMapping("/logout")
    public ResultBean userLoginOut(){
        ResultBean resultBean = ResultBean.success();
        resultBean.setMsg("退出登录成功");
        // 会销毁，在SessionListener监听session销毁，清理权限缓存
        SecurityUtils.getSubject().logout();
        return resultBean;
    }

    @RequestMapping("/unAuth")
    public ResultBean unAuth(HttpServletRequest request, HttpServletResponse response){
        ResultBean resultBean = ResultBean.success();
        resultBean.setSuccess(false);
        resultBean.setCode(HttpStatus.UNAUTHORIZED.value());
        resultBean.setMsg("未登录，无法访问地址");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return resultBean;
    }
}