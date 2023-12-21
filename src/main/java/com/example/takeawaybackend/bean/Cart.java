package com.example.takeawaybackend.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Cart {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer shopId;
    @TableField(exist = false)
    private String shopName;
    private String detailJson;
}
