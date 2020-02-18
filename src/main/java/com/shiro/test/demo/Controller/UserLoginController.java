package com.shiro.test.demo.Controller;

import com.shiro.test.demo.Entity.User;
import com.shiro.test.demo.Service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Code by langlang on 2020/2/17
 */

@Controller
public class UserLoginController {

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    //最好待会集成一下错误页面处理

    @RequestMapping("/addUser")
    public String addUser(){
        return "add";
    }

    @RequestMapping("/updateUser")
    public String updateUser(){
        return "update";
    }

    @RequestMapping("/tologin")
    public String tologin(){
        return "login";
    }

    @RequestMapping("/login")
    public String login(String name, String password, Model model){
        //处理登陆逻辑
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(name,password);

        try {
            subject.login(usernamePasswordToken);
            return "redirect:/index";
        }catch (UnknownAccountException e){
            model.addAttribute("name","用户名不存在");
            return "login";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("name","密码错误");
            return "login";
        }
    }

    @RequestMapping("/regist")
    public String regist(){
        return "regist";
    }

    @Autowired
    UserService userService;

    @RequestMapping("/regist.do")
    public String registDo(User user,Model model){
        if(userService.findByName(user.getName())!=null){
            model.addAttribute("msg","该用户名已被使用");
            return "regist";
        }else{
            int result = userService.addUser(user.getName(), user.getPassword());
            if(result!=0){
                //注册成功
                //跳转到登陆界面
                //Session和cookie还没有实现
                //提示界面,几秒钟后跳转到登陆界面
                return "RegistSuccess";
            }else{
                model.addAttribute("msg","注册失败");
                return "regist";
            }
        }
    }

    @RequestMapping("/noAuth")
    public String noAuth(){
        return "noAuth";
    }
}
