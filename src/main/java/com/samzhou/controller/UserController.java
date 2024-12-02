package com.samzhou.controller;


import com.samzhou.pojo.Result;
import com.samzhou.pojo.User;
import com.samzhou.service.UserService;
import com.samzhou.utils.JwtUtil;
import com.samzhou.utils.Md5Util;
import com.samzhou.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

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
            ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
            stringStringValueOperations.set(token,token,1, TimeUnit.HOURS);
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

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam("avatarUrl") @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params,@RequestHeader("Authorization") String token){
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if(!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(oldPwd)){
            return Result.success("缺少必要参数");
        }

        Map<String,Object> claims = ThreadLocalUtil.get();
        String username = claims.get("username").toString();
        User user = userService.findByUserName(username);
        if(!user.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.success("旧密码输入错误");
        }
        if(!newPwd.equals(rePwd)){
            return Result.success("两次密码输入错误");
        }
        userService.updatePwd(newPwd);

        //删除旧token
        stringRedisTemplate.delete(token);

        return Result.success();

    }




}
