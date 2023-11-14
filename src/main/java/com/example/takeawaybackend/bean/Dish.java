package com.example.takeawaybackend.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Dish {
    @TableId(type= IdType.AUTO)
    private Integer id;
    @TableField(exist = false)
    private String idStr;
    private String dishName;
    private String picture;
    private Float price;
    private Integer number;
    private String saleState;
    private Integer sale;//销售量
    private String detail;//描述
    private Float pack;//打包费
    private String weight;//分量
    private String material;//原料

}
