package com.samzhou.service;

import com.samzhou.pojo.User;

public interface UserService {
    User findByUserName(String username);

    void register(String username, String password);
}
