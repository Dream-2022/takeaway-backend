package com.example.takeawaybackend.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class DishFlavor {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private Integer attributeId;
    private String flavorName;
    private Float price;
}
