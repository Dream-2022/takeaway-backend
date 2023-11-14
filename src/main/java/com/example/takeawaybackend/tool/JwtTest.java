package com.example.takeawaybackend.tool;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JwtTest {
    public static String KEY="!Q1ggggggggggggggggggggggggggggggggggggggggggggggfjyfgmffgfghfhfgnfghfnhgfmgh";
    public static long time=10*3600*1000;
    public static long longTime=2*24*3600*1000;
    public static Map<String,Object> getToken(String username, String isAdmin){
        Map<String,Object>tokenmap=new HashMap<>();
        JwtBuilder jwtBuilder= Jwts.builder();
        String token = jwtBuilder
                .setHeaderParam("type","JWT")
                .setHeaderParam("alg","HS256")
                .claim("username",username)
                .claim("isAdmin",isAdmin)
                .setSubject("login")
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256, KEY)
                .compact();
        String long_token=jwtBuilder
                .setHeaderParam("type","JWT")
                .setHeaderParam("alg","HS256")
                .claim("username",username)
                .claim("isAdmin",isAdmin)
                .setSubject("login")
                .setExpiration(new Date(System.currentTimeMillis() + longTime))
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256, KEY)
                .compact();
        tokenmap.put("token",token);
        tokenmap.put("long_token",long_token);
        return tokenmap;
    }
    //判断token和long_token是否过期
    public static int isToken(String token,String long_token){
        System.out.println("token:"+token);
        System.out.println("long_token:"+long_token);
        if(long_token==null||long_token.equals("")){
            return 0;
        }

        Jws<Claims> claimsJws=null;
        try{
            System.out.println("KEY:"+KEY);
            claimsJws= Jwts.parser().setSigningKey(KEY).parseClaimsJws(long_token);
            System.out.println(claimsJws);
        }catch (ExpiredJwtException e){
            e.printStackTrace();
            return 0;
        }

        if(token==null||token.equals("")){
            return 0;
        }
        try{
            claimsJws=Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
            System.out.println(claimsJws);
        }catch (ExpiredJwtException e){
            e.printStackTrace();
            return 1;//token过期，long_token未过期
        }catch (JwtException e) {
            // 其他异常处理
            e.printStackTrace();
            // 进行适当的处理
        }
        return 2;//token,long_token未过期
    }
}
