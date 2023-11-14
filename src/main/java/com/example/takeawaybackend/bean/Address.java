package com.example.takeawaybackend.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Address {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String myName;
    private Integer user_id;
    private String phone;
    private String addressProvince;
    private String addressCity;
    private String addressCounty;
    @TableField(exist = false)
    private String province;
    @TableField(exist = false)
    private String city;
    @TableField(exist = false)
    private String county;
    private String addressDetail;
    private String addressDefault;
}
