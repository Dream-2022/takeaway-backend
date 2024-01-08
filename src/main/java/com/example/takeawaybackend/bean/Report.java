package com.example.takeawaybackend.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Report {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer shopId;
    private String content;
    private String state;
    @TableField(exist = false)
    private User user;
    @TableField(exist = false)
    private Shop shop;
}
