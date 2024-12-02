package com.samzhou.interceptors;


import com.samzhou.utils.JwtUtil;
import com.samzhou.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor  implements HandlerInterceptor {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //验证令牌
        String token = request.getHeader("Authorization"); //每次请求都会带上 Authorization
        try{

            //从redis中获取相同的value
            ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
            String redisToken =  stringStringValueOperations.get(token);
            if(null== redisToken){
                throw new RuntimeException("Token失效");
            }

            //验证token
            Map<String,Object> claims = JwtUtil.parseToken(token);

            //业务数据房间ThreadLocal
            ThreadLocalUtil.set(claims);

            //success
            return true;
        }catch (Exception e){
            response.setStatus(401);
            return false;
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清楚数据
        ThreadLocalUtil.remove();
    }
}
