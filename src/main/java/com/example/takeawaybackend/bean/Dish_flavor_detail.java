package com.example.takeawaybackend.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Dish_flavor_detail {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private Integer flavor_id;
    private String detail;


}
