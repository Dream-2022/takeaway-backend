package com.example.takeawaybackend.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class Refund {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private Integer shopId;
    private Integer orderId;
    private Integer userId;
    private String content;
}
