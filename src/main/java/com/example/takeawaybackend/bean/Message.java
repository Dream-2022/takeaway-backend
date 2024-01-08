package com.example.takeawaybackend.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Message {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private Integer senderId;
    private Integer receiverId;
    private String content;
    private Date createTime;
    @TableField(exist = false)
    private User receiver;
    @TableField(exist = false)
    private User sender;
    @TableField(exist = false)
    private List<User> userList;
}
