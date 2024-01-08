package com.example.takeawaybackend.pojo;

import com.example.takeawaybackend.bean.Message;

import java.util.List;

public class MessageUserList {
    private List<Message> userList;

    public List<Message> getUserList() {
        return userList;
    }

    public void setUserList(List<Message> userList) {
        this.userList = userList;
    }
}
