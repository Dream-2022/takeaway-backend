package com.example.takeawaybackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.takeawaybackend.bean.Message;
import com.example.takeawaybackend.bean.User;
import com.example.takeawaybackend.dao.MessageDao;
import com.example.takeawaybackend.dao.UserDao;
import com.example.takeawaybackend.pojo.MessageData;
import com.example.takeawaybackend.tool.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/pre/message")
public class MessageController {
    @Autowired
    private MessageDao messageDao;
    @Autowired
    private UserDao userDao;
    //插入一条举报
    @PostMapping("/selectMessageByUserId")
    public DataResult selectMessageByUserId(@RequestBody MessageData messageData){
        System.out.println("selectMessageByUserId"+messageData.getUserId());

        QueryWrapper<Message> wrapper=new QueryWrapper<Message>()
                .eq("receiver_id",messageData.getUserId())
                .or()
                .eq("sender_id",messageData.getUserId());

        List<Message> messageList=messageDao.selectList(wrapper);
        Map<Integer,User> userMap=new HashMap<>();
        List<User> userList=new ArrayList<>();
        for (Message message : messageList) {
            QueryWrapper<User> wrapper1=new QueryWrapper<User>()
                    .eq("id",message.getReceiverId());
            User user1=userDao.selectOne(wrapper1);
            if(user1.getPicture()==null){
                user1.setPicture("http://localhost:8080/upload/headSculpture.jpeg");
            }
            message.setReceiver(user1);
            QueryWrapper<User> wrapper2=new QueryWrapper<User>()
                    .eq("id",message.getSenderId());
            User user2=userDao.selectOne(wrapper2);
            if(user2.getPicture()==null){
                user2.setPicture("http://localhost:8080/upload/headSculpture.jpeg");
            }
            message.setSender(user2);
            if(!Objects.equals(message.getReceiverId(), messageData.getUserId())&&!userMap.containsKey(message.getReceiverId())){
                System.out.println("加入："+message.getReceiverId());
                userMap.put(message.getReceiverId(),user1);
                userList.add(user1);
            }
            if(!Objects.equals(message.getSenderId(), messageData.getUserId())&&!userMap.containsKey(message.getSenderId())){
                System.out.println("加入："+message.getSenderId());
                userMap.put(message.getSenderId(),user2);
                userList.add(user2);
            }
        }
        if(messageList.size()!=0){
            messageList.get(0).setUserList(userList);
        }
        System.out.println(messageData);
        return DataResult.success(messageList);
    }
    //从数据库获取是否有两个user的聊天记录
    @PostMapping("/selectMessageByTwoUserId")
    public DataResult selectMessageByTwoUserId(@RequestBody MessageData messageData){
        System.out.println("selectMessageByTwoUserId"+messageData.getUserId());

        QueryWrapper<Message> wrapper=new QueryWrapper<Message>()
                .eq("receiver_id",messageData.getReceiverId())
                .eq("sender_id",messageData.getSenderId())
                .or()
                .eq("sender_id",messageData.getReceiverId())
                .eq("receiver_id",messageData.getSenderId());

        List<Message>messageList=messageDao.selectList(wrapper);
        for (Message message : messageList) {
            QueryWrapper<User> wrapper1=new QueryWrapper<User>()
                    .eq("id",message.getReceiverId());
            User user1=userDao.selectOne(wrapper1);
            if(user1.getPicture()==null){
                user1.setPicture("http://localhost:8080/upload/headSculpture.jpeg");
            }
            message.setReceiver(user1);
            QueryWrapper<User> wrapper2=new QueryWrapper<User>()
                    .eq("id",message.getSenderId());
            User user2=userDao.selectOne(wrapper2);
            if(user2.getPicture()==null){
                user2.setPicture("http://localhost:8080/upload/headSculpture.jpeg");
            }
            message.setSender(user2);
        }
        System.out.println(messageData);
        return DataResult.success(messageList);
    }
}
