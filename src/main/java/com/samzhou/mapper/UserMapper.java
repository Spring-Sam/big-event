package com.samzhou.mapper;

import com.samzhou.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user where username = #{username}")
    User findByUserName(String username);

    @Insert("INSERT INTO user(username,password,create_time,update_time) values(#{username},#{password},now(),now())")
    void add(String username, String password);
}
