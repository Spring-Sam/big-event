package com.samzhou.service.impl;

import com.samzhou.mapper.UserMapper;
import com.samzhou.pojo.User;
import com.samzhou.service.UserService;
import com.samzhou.utils.Md5Util;
import com.samzhou.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

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

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String,Object> claims = ThreadLocalUtil.get();
        Integer id  = Integer.valueOf(claims.get("id").toString());
        userMapper.updateAvatar(avatarUrl,id);
    }

    @Override
    public void updatePwd(String newPwd) {
        Map<String,Object> claims = ThreadLocalUtil.get();
        Integer id  = Integer.valueOf(claims.get("id").toString());
        userMapper.updatePwd(Md5Util.getMD5String(newPwd),id);
    }
}
