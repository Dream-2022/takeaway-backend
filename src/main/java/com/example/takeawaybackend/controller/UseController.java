package com.example.takeawaybackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.takeawaybackend.bean.User;
import com.example.takeawaybackend.dao.UserDao;
import com.example.takeawaybackend.pojo.LoginData;
import com.example.takeawaybackend.tool.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/pre/use")
public class UseController {
    @Autowired
    private UserDao userDao;
    //分页：通过userId和pageNum和gender和nickname查找所有用户
    @PostMapping("/selectByIdAndGenderAndNickname")
    public DataResult selectByIdAndGenderAndNickname(@RequestBody LoginData loginData){
        System.out.println("selectByIdAndGenderAndNickname收到的数据是："+loginData.getIdValue()+','+loginData.getNickname()+loginData.getGender()+","+loginData.getPageNum());
        String gender="";
        if(loginData.getGender().equals("0")){
            System.out.println("全部");
            gender="全部";
        }
        if(loginData.getGender().equals("1")){
            System.out.println("男");
            gender="男";
        }
        if(loginData.getGender().equals("2")){
            System.out.println("女");
            gender="女";
        }
        IPage<User> page=new Page<>();
        page.setCurrent(loginData.getPageNum());
        page.setSize(5);
        QueryWrapper<User> wrapper=new QueryWrapper<User>()
                .like(!loginData.getIdValue().equals(""),"id",loginData.getId())
                .eq(!gender.equals("全部"),"gender",gender)
                .like(!loginData.getNickname().equals(""),"nickname",loginData.getNickname());
        IPage<User> iPageList = userDao.selectPage(page,wrapper);
        List<User>userList=new ArrayList<>();
        for (User user : iPageList.getRecords()) {
            if(user.getPicture()==null||user.getPicture().equals("")){
                user.setPicture("http://localhost:8080/upload/headSculpture.jpeg");
            }
            if (user.getProfile()==null||user.getProfile().equals("")) {
                user.setProfile("该用户很懒，什么也没写");
            }
            if(user.getGender()==null||user.getGender().equals("")){
                user.setGender("未知");
            }
            user.setPageNum((int) iPageList.getPages());
            userList.add(user);
        }
        System.out.println(userList);
        return DataResult.success(userList);
    }
    //通过userId查找user
    @PostMapping("/selectUserById")
    public DataResult selectUserById(@RequestBody LoginData loginData){
        System.out.println("selectUserById："+loginData.getId());
        QueryWrapper<User> wrapper=new QueryWrapper<User>()
                .eq("id",loginData.getId());
        User user=userDao.selectOne(wrapper);
        System.out.println(user);
        return DataResult.success(user);
    }
    //通过userId删除用户
    @PostMapping("/deleteUserById")
    public DataResult deleteUserById(@RequestBody LoginData loginData){
        System.out.println("deleteUserById："+loginData.getId());
        QueryWrapper<User> wrapper=new QueryWrapper<User>()
                .eq("id",loginData.getId());
        int result=userDao.delete(wrapper);
        System.out.println("删除："+result);
        return DataResult.success("删除成功");
    }
}
