package com.example.takeawaybackend.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Shopping_cart {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private Integer user_id;
}
