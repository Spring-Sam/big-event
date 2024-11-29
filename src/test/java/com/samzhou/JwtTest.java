package com.samzhou;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

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










}
