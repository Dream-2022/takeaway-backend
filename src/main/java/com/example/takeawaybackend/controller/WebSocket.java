package com.example.takeawaybackend.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.takeawaybackend.bean.Message;
import com.example.takeawaybackend.bean.User;
import com.example.takeawaybackend.dao.MessageDao;
import com.example.takeawaybackend.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@Slf4j
@ServerEndpoint("/api/websocket/{userId}")  // 接口路径 ws://localhost:8087/webSocket/userId;
public class WebSocket {
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private static ApplicationContext applicationContext;
    /**
     * 用户ID
     */
    private String userId;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    //虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
    //  注：底下WebSocket是当前类名
    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();
    // 用来存在线连接用户信息
    private static ConcurrentHashMap<String, Session> sessionPool = new ConcurrentHashMap<String, Session>();

    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebSocket.applicationContext = applicationContext;
    }

    /**
     * 链接成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId) {
        try {
            this.session = session;
            this.userId = userId;
            webSockets.add(this);
            sessionPool.put(userId, session);
            log.info("【websocket消息】有新的连接，总数为:" + webSockets.size());

//            this.session.getBasicRemote().sendText("我想死");

        } catch (Exception e) {
        }
    }

    /**
     * 链接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        try {
            webSockets.remove(this);
            sessionPool.remove(this.userId);
            log.info("【websocket消息】连接断开，总数为:" + webSockets.size());


        } catch (Exception e) {
        }
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        MessageDao messageDao=applicationContext.getBean(MessageDao.class);
        UserDao userDao=applicationContext.getBean(UserDao.class);

        log.info("【websocket消息】收到客户端消息:" + message);
        System.out.println(JSON.parse(message));
        Map<String, Object> map = (Map<String, Object>) (JSON.parse(message));
        System.out.println(map);
        Message messageX = BeanUtil.fillBeanWithMap(map, new Message(), false);
        Date create_at=new Date();
        messageX.setCreateTime(create_at);
        System.out.println(messageX);
        //将获取到的消息，插入到后端
        int insert=messageDao.insert(messageX);
        QueryWrapper<User> wrapper1=new QueryWrapper<User>()
                .eq("id",messageX.getReceiverId());
        User user1=userDao.selectOne(wrapper1);
        if(user1.getPicture()==null){
            user1.setPicture("http://localhost:8080/upload/headSculpture.jpeg");
        }
        messageX.setReceiver(user1);
        QueryWrapper<User> wrapper2=new QueryWrapper<User>()
                .eq("id",messageX.getSenderId());
        User user2=userDao.selectOne(wrapper2);
        if(user2.getPicture()==null){
            user2.setPicture("http://localhost:8080/upload/headSculpture.jpeg");
        }
        messageX.setSender(user2);
        System.out.println("插入："+insert);
        System.out.println(messageX);

        for (Object value : map.values()) {
            System.out.println(value);
        }
        //接着前端接收
        Session session1 = sessionPool.get(messageX.getReceiverId().toString());
        Session session2 = sessionPool.get(messageX.getSenderId().toString());
        System.out.println(session1);
        System.out.println(session2);
        if(session1!=null){
            System.out.println("发送1");
            session1.getBasicRemote().sendText(JSON.toJSONString(messageX));
        }
        if(session2!=null){
            System.out.println("发送2");
            session2.getBasicRemote().sendText(JSON.toJSONString(messageX));
        }
    }

    /** 发送错误时的处理
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {

        log.error("用户错误,原因:"+error.getMessage());
        error.printStackTrace();
    }


    // 此为广播消息
    public void sendAllMessage(String message) {
        log.info("【websocket消息】广播消息:"+message);
        for(WebSocket webSocket : webSockets) {
            try {
                if(webSocket.session.isOpen()) {
                    webSocket.session.getAsyncRemote().sendText(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 此为单点消息
    public void sendOneMessage(String userId, String message) {
        Session session = sessionPool.get(userId);
        if (session != null&&session.isOpen()) {
            try {
                log.info("【websocket消息】 单点消息:"+message);
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 此为单点消息(多人)
    public void sendMoreMessage(String[] userIds, String message) {
        for(String userId:userIds) {
            Session session = sessionPool.get(userId);
            if (session != null&&session.isOpen()) {
                try {
                    log.info("【websocket消息】 单点消息:"+message);
                    session.getAsyncRemote().sendText(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

}