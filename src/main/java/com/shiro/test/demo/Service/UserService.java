package com.shiro.test.demo.Service;

import com.shiro.test.demo.Entity.User;
import org.springframework.stereotype.Service;

/**
 * Code by langlang on 2020/2/17
 */

@Service
public interface UserService {

    public int addUser(String name,String password);

    public User findByName(String name);

    public User findByID(int ID);
}
