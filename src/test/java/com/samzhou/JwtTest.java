package com.samzhou;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


//会先初始化spring容器，然后可以从容器中拿对象
@SpringBootTest
public class JwtTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate; //从容器中获取对象

    @Test
    public void testGen(){

        Map<String,Object> claims = new HashMap<String,Object>();
        claims.put("id",1);
        claims.put("username","jack");

        String token = JWT.create()
                .withClaim("user",claims)
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*3))
                .sign(Algorithm.HMAC256("itheima"));

        System.out.println(token);

    }

    @Test
    public void testRedis(){
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        stringStringValueOperations.set("username","1111");
        stringStringValueOperations.set("jack","100",15, TimeUnit.MINUTES);

    }

    @Test
    public void testGet(){
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        System.out.println(stringStringValueOperations.get("username"));

    }









}
