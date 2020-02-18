package com.shiro.test.demo.Mapper;

import com.shiro.test.demo.Entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Code by langlang on 2020/2/17
 */
@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user(name,password) values(#{name},#{password})")
    public int addUser(String name,String password);

    @Select("SELECT * FROM user WHERE name = #{name}")
    public User fingByName(String name);

    @Select("SELECT * FROM user WHERE id = #{ID}")
    public User findByID(int ID);
}
