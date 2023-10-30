package com.example.takeawaybackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.takeawaybackend.bean.User;
import com.example.takeawaybackend.dao.UserDao;
import com.example.takeawaybackend.pojo.LoginData;
import com.example.takeawaybackend.tool.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.takeawaybackend.tool.ResponseCode.NICKNAME_REPEAT;

@RestController
@RequestMapping("/api/pre/editInformation")
public class EditController {
    public static User userStatic;
    @Autowired
    private UserDao userDao;
    @PostMapping("/nickname")
    public DataResult nickname(@RequestBody LoginData loginData){
        System.out.println("收到的数据是："+loginData.getNickname()+','+loginData.getOldValue());
        QueryWrapper<User> wrapper=new QueryWrapper<User>()
                .eq("nickname",loginData.getNickname());
        User user1=userDao.selectOne(wrapper);

        //该昵称被注册的情况
        if(user1!=null){
            return DataResult.fail(NICKNAME_REPEAT);
        }
        //修改昵称
        User user=new User();
        user.setNickname(loginData.getNickname());
        System.out.println(user);
        QueryWrapper<User> wrapper1=new QueryWrapper<User>()
                .eq("nickname",loginData.getOldValue());
        int flag=userDao.update(user,wrapper1);
        System.out.println(flag);
        return DataResult.success(user);
    }
    @PostMapping("/gender")
    public DataResult gender(@RequestBody LoginData loginData){
        System.out.println("收到的数据是："+loginData.getGender()+','+loginData.getUsername());
        //修改性别
        User user=new User();
        user.setGender(loginData.getGender());
        System.out.println(user);
        QueryWrapper<User> wrapper1=new QueryWrapper<User>()
                .eq("username",loginData.getUsername());
        int flag=userDao.update(user,wrapper1);
        System.out.println(flag);
        return DataResult.success(user);
    }
    @PostMapping("/profile")
    public DataResult profile(@RequestBody LoginData loginData){
        System.out.println("收到的数据是："+loginData.getProfile()+','+loginData.getUsername());
        //修改简介
        User user=new User();
        user.setProfile(loginData.getProfile());
        System.out.println(user);
        QueryWrapper<User> wrapper1=new QueryWrapper<User>()
                .eq("username",loginData.getUsername());
        int flag=userDao.update(user,wrapper1);
        System.out.println(flag);
        return DataResult.success(user);
    }
    public static Timestamp convertToTimestamp(String dateStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date parsedDate = dateFormat.parse(dateStr);
            return new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // 处理转换错误
        }
    }
    @PostMapping("/birth")
    public DataResult birth(@RequestBody LoginData loginData){
        System.out.println("收到的数据是："+loginData.getBirth()+','+loginData.getUsername());
        //修改生日
        User user=new User();
        Timestamp timestamp = convertToTimestamp(loginData.getBirth());
        System.out.println(timestamp);
        user.setBirthday(timestamp);
        System.out.println("user"+user);
        QueryWrapper<User> wrapper1=new QueryWrapper<User>()
                .eq("username",loginData.getUsername());
        int flag=userDao.update(user,wrapper1);

        User user1=userDao.selectOne(wrapper1);
        System.out.println("user1"+user1);
        System.out.println(flag);
        return DataResult.success(user);
    }
}
