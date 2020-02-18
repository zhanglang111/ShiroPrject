package com.shiro.test.demo.Service.ServiceImpl;

import com.shiro.test.demo.Entity.User;
import com.shiro.test.demo.Mapper.UserMapper;
import com.shiro.test.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Code by langlang on 2020/2/17
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public int addUser(String name,String password) {
        return userMapper.addUser(name,password);
    }

    @Override
    public User findByName(String name) {
        return userMapper.fingByName(name);
    }

    @Override
    public User findByID(int ID) {
        return userMapper.findByID(ID);
    }
}
