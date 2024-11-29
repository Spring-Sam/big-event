package com.samzhou.controller;


import com.samzhou.pojo.Result;
import com.samzhou.pojo.User;
import com.samzhou.service.UserService;
import com.samzhou.utils.JwtUtil;
import com.samzhou.utils.Md5Util;
import com.samzhou.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password){

        User u = userService.findByUserName(username);
        if(null == u ){
            userService.register(username,password);
            return Result.success();

        }else{
            return Result.error("用户名已经占用");
        }
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password){
        User loginUser =  userService.findByUserName(username);
        if(loginUser == null){
            return Result.error("用户名错误");
        }
        if(Md5Util.getMD5String(password).equals(loginUser.getPassword())){
            Map<String,Object> claims= new HashMap<>();
            claims.put("id",loginUser.getId());
            claims.put("username",loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    @GetMapping("/userinfo")
    public Result<User> getUserInfo(){
        //根据token获取用户名
        Map<String,Object> claims = ThreadLocalUtil.get();
        String username = claims.get("username").toString();
        User user = userService.findByUserName(username);
        return Result.success(user);


    }







}
