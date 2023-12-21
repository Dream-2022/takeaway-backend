package com.example.takeawaybackend.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class DishOrder {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer shopId;
    private String state;
    private String notes;
    private Integer tablewareNum;
    private String addressValue;
    private String dishValue;
    private Date updateTime;
    private String phone;
    @TableField(exist = false)
    private Integer pageNum;//页数
}
