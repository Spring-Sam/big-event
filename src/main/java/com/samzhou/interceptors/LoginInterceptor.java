package com.samzhou.interceptors;


import com.samzhou.utils.JwtUtil;
import com.samzhou.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor  implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //验证令牌
        String token = request.getHeader("Authorization");
        try{
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
