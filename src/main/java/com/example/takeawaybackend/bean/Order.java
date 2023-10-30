package com.example.takeawaybackend.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Order {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private Integer user_id;
    private Integer address_id;
    private String notes;


}
