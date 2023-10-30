package com.example.takeawaybackend.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Address {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private Integer user_id;
    private String label;
    private String phone;
    private String address_name;
}
