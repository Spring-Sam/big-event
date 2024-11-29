package com.samzhou.service.impl;

import com.samzhou.mapper.UserMapper;
import com.samzhou.pojo.User;
import com.samzhou.service.UserService;
import com.samzhou.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public void register(String username, String password) {
        String md5Password = Md5Util.getMD5String(password);
        userMapper.add(username,md5Password);

    }
}
