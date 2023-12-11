package com.example.takeawaybackend.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

@Data
public class DishAttribute {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private Integer dishId;
    private String attributeName;
    private String checked;
    @TableField(exist = false)
    private List<DishFlavor> flavorList;
}
