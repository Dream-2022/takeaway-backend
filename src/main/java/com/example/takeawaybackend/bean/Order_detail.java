package com.example.takeawaybackend.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Order_detail {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private Integer shopping_id;
    private Integer dish_id;


}
