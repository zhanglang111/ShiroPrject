package com.shiro.test.demo.Entity;

import com.shiro.test.demo.Service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Code by langlang on 2020/2/17
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo ();

        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        User dbuser =  userService.findByID(user.getId());
        info.addStringPermission(dbuser.getPerms());
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("处理认证逻辑");

        //现在用真实数据
        //使用用户名进行查找
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        User user = userService.findByName(token.getUsername());
        if(user == null){
            return null;
        }else {
            return new SimpleAuthenticationInfo(user,user.getPassword(),"");
        }

    }
}
