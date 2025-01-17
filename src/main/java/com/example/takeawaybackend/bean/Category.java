package com.example.takeawaybackend.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

@Data
public class Category {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private Integer shopId;
    private String categoryName;
    @TableField(exist = false)
    private List<Dish> dishList;
}
