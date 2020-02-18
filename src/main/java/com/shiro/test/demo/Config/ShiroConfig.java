package com.shiro.test.demo.Config;

import com.shiro.test.demo.Entity.User;
import com.shiro.test.demo.Entity.UserRealm;
import com.shiro.test.demo.Service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Code by langlang on 2020/2/17
 */

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager")DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        Map<String,String> filterMap = new LinkedHashMap<String,String>();
//        filterMap.put("/addUser","authc");
//        filterMap.put("/updateUser","authc");
        filterMap.put("/index", "anon");
        filterMap.put("/login", "anon");
        filterMap.put("/regist", "anon");
        filterMap.put("/regist.do", "anon");
        filterMap.put("/RegistSuccess", "anon");
//        filterMap.put("/noAuth", "anon");
        filterMap.put("/addUser","perms[user:add]");//这个必须放在/*前面
        filterMap.put("/updateUser","perms[user:update]");//这个必须放在/*前面
        //取消硬编码

//        filterMap.put("/addUser","perms[user:add]");
        filterMap.put("/*", "authc");

        filterFactoryBean.setLoginUrl("/tologin");

        //设置没有权限界面
        filterFactoryBean.setUnauthorizedUrl("/noAuth");

        filterFactoryBean.setFilterChainDefinitionMap(filterMap);

        return filterFactoryBean;
    }
    @Bean(name = "defaultWebSecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }
    @Bean(name = "userRealm")
    public UserRealm getRealm() {
        return new UserRealm();
    }
}
