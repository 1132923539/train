package com.canway.train.config;


import com.baomidou.mybatisplus.mapper.Condition;
import com.canway.train.entity.UserDO;
import com.canway.train.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Hanson
 */
public class MyShiroRealm extends AuthorizingRealm {
    @Resource
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        return authorizationInfo;
    }

    /**
     * 主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
                //获取用户的输入的账号.
                String username = (String) token.getPrincipal();
                //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
                List<UserDO> userInfoList = (List)userService.selectList(Condition.create().eq("account",username));
                if (userInfoList.size() == 0) {
                    // 没找到帐号
                    throw new UnknownAccountException();
                }
                 //用户名，密码，realm名字
                UserDO userInfo = userInfoList.get(0);
                SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username,userInfo.getPassword(),getName());
                Session session = SecurityUtils.getSubject().getSession();
                session.setAttribute("userSession", userInfo);
                session.setAttribute("accountName",userInfo.getAccount());
                session.setAttribute("userName",userInfo.getName());
                session.setAttribute("userId",userInfo.getId());
                return authenticationInfo;
    }

}
