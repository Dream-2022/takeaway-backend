package com.example.takeawaybackend.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

@Data
public class Dish {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String dishName;
    private String picture;
    private Float price;
    private Integer categoryId;//店内种类id
    @TableField(exist = false)
    private String categoryName;
    private Integer number;//库存量（-1为充足， 0为无库存）
    private String saleState;//销售状态（1：可售；0：不可售）
    private Integer sale;//销售量
    private String detail;//描述
    private Float pack;//打包费
    private String weight;//分量
    private String material;//原料
    @TableField(exist = false)
    private Integer pageNum;//页数
    @TableField(exist = false)
    private List<DishAttribute> attributeList;
}
