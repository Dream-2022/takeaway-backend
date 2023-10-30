package com.example.takeawaybackend.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Dish {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private Integer dish_name;
    private String picture;
    private Integer number;
    private String sale_state;
    //销售量
    private Integer sale;
    private String detail;

}
