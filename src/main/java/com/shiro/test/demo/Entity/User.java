package com.shiro.test.demo.Entity;

import lombok.Data;

/**
 * Code by langlang on 2020/2/17
 */
@Data
public class User {
    private Integer id;
    private String name;
    private String password;
    private String perms;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public String getPerms() {
        return perms;
    }
}
