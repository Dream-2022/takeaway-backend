package com.example.takeawaybackend.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Shop {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private Integer userId;
    private String name;
    private String detail;
    private String state;
    private String picture;
    private String background;
    private Integer saleNum;
    @TableField(exist = false)
    private String saleStr;
    private Float packing;
    private Timestamp createAt;
    //评分
    private Float score;
    private Float begin;
    private String type;
}
