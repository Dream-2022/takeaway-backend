package com.example.takeawaybackend.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Remark {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer orderId;
    private String content;
    private Float star;
    private String images;
    private Date createTime;
    @TableField(exist = false)
    private DishOrder dishOrder;
    @TableField(exist = false)
    private User user;

}
