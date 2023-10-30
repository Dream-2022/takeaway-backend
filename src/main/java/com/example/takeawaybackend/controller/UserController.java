package com.example.takeawaybackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.takeawaybackend.bean.User;
import com.example.takeawaybackend.dao.UserDao;
import com.example.takeawaybackend.pojo.LoginData;
import com.example.takeawaybackend.tool.DataResult;
import com.example.takeawaybackend.tool.ObtainUsername;
import com.example.takeawaybackend.tool.SendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.example.takeawaybackend.tool.ResponseCode.*;

@RestController
@RequestMapping("/api/pre")
public class UserController {
    public static User userStatic;
    @Autowired
    private UserDao userDao;
    private static Map<String, String> map = new HashMap<>();
//    @CrossOrigin
    @PostMapping("/login")
    public DataResult login(@RequestBody LoginData loginData){
        System.out.println("收到的数据是："+loginData.getUsername()+","+loginData.getPassword());
        QueryWrapper<User> wrapper=new QueryWrapper<User>()
                .eq("username",loginData.getUsername())
                .eq("password",loginData.getPassword());
        User user1=userDao.selectOne(wrapper);

        QueryWrapper<User> wrapper1=new QueryWrapper<User>()
                .eq("username",loginData.getUsername());
        User user2=userDao.selectOne(wrapper1);
        //账号不存在的情况
        if(user2==null){
            return DataResult.fail(ACCOUNT_NOT_FOUND);
        }
        System.out.println(user1);
        if(user1==null){
            return DataResult.fail(ACCOUNT_ERROR);
        }
        userStatic=user1;
        return DataResult.success(user1);
    }
    //获取邮箱验证码
    @PostMapping("/obtainCode")
    public DataResult obtainCode(@RequestBody LoginData loginData) throws MessagingException {
        System.out.println("收到的数据是："+loginData.getEmail());
        //发送邮箱
        Random r=new Random();
        int random = r.nextInt(1000000);
        String strCode=String.format("%06d", random);
        String str="验证码为："+strCode+"，此验证码三分钟内有效。";
        System.out.println("发送邮箱前");
        map.put(loginData.getEmail(),strCode);
        SendEmail.sendEmail(loginData.getEmail(),"饿了么",str,loginData.getEmail());
        return DataResult.success("成功获取");
    }
    //找回密码
    @PutMapping("/modifyPassword")
    public DataResult modifyPassword(@RequestBody LoginData loginData) throws MessagingException {
        System.out.println("收到的数据是："+loginData.getEmail()+','+loginData.getCode()+loginData.getPassword());
        QueryWrapper<User> wrapper=new QueryWrapper<User>()
                .eq("email",loginData.getEmail());
        User user1=userDao.selectOne(wrapper);
        //该邮箱未被注册
        if(user1==null){
            return DataResult.fail(EMAIL_NOT_FOUND);
        }
        //验证码不正确的情况
        if(!map.get(loginData.getEmail()).equals(loginData.getCode())){
            return DataResult.fail(CODE_ERROR);
        }
        //修改密码
        User user=new User();
        user.setPassword(loginData.getPassword());
        System.out.println(user);
        QueryWrapper<User> wrapper1=new QueryWrapper<User>()
                .eq("email",loginData.getEmail());
        userDao.update(user,wrapper1);
        return DataResult.success(user);
    }
    //注册
    @PostMapping("/register")
    public DataResult register(@RequestBody LoginData loginData) throws MessagingException {
        System.out.println("收到的数据是："+loginData.getEmail()+','+loginData.getCode()+loginData.getPassword());
        QueryWrapper<User> wrapper=new QueryWrapper<User>()
                .eq("email",loginData.getEmail());
        User user1=userDao.selectOne(wrapper);
        //该邮箱已被注册
        if(user1!=null){
            return DataResult.fail(EMAIL_ERROR);
        }
        //验证码不正确的情况
        if(!map.get(loginData.getEmail()).equals(loginData.getCode())){
            return DataResult.fail(CODE_ERROR);
        }
        //生成一个唯一账号
        String newStr = ObtainUsername.obtainUsername();
        while(true){
            QueryWrapper<User> wrapper2=new QueryWrapper<User>()
                    .eq("email",loginData.getEmail());
            User user2=userDao.selectOne(wrapper2);
            if(user2==null){
                break;
            }
            newStr= ObtainUsername.obtainUsername();
        }
        System.out.println(2);
        User user = new User();
        user.setUsername(newStr);
        user.setUserType("customer");
        user.setEmail(loginData.getEmail());
        user.setPassword(loginData.getPassword());
        int insert = userDao.insert(user);
        System.out.println(insert);
        return DataResult.success(user);
    }
}
