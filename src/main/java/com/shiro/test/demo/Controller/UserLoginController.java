package com.shiro.test.demo.Controller;

import com.alibaba.fastjson.JSONObject;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
    public String login(HttpServletRequest request, HttpServletResponse response,User user, Model model){

//        //首先查看是否存在cookie、session等
//        Cookie[] cookies = request.getCookies();
//        if(cookies!=null){
//            for (Cookie cookie : cookies) {
//                System.out.println(cookie.getName() + "--" + cookie.getValue());
//                if (cookie.getValue().equals(user.getName())){
//                    //直接放行
//
//                }
//            }
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put(user.getName(),user.getName());
//            jsonObject.put(user.getPassword(),user.getPassword());
//            Cookie cookie = new Cookie(user.getName(),);
//            response.addCookie(cookie);
//        }else{
//            System.out.println("没有Cookie...");
//        }

        //使用cookie不能以json格式保存用户信息，现在改用session
        HttpSession session = request.getSession(false);

        if(session!=null){
            //获取session
            User userSession = (User) session.getAttribute(user.getName());
            if(userSession==null){
                //处理登陆逻辑
                Subject subject = SecurityUtils.getSubject();
                UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getName(),user.getPassword());
                try {
                    subject.login(usernamePasswordToken);
                    //设置session
                    HttpSession newUserSession = request.getSession();
                    //自动生成了sessionid
                    if(newUserSession!=null){
                        newUserSession.setAttribute(user.getName(),user);
                    }
                    return "redirect:/index";
                }catch (UnknownAccountException e){
                    model.addAttribute("name","用户名不存在");
                    return "login";
                }catch (IncorrectCredentialsException e){
                    model.addAttribute("name","密码错误");
                    return "login";
                }
            }else{
                if(userSession.getName().equals(user.getName())&&userSession.getPassword().equals(user.getPassword())) {
                    //直接登陆
                    return "index";
                }else{
                    return "login";
                }
            }
        }else{
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
