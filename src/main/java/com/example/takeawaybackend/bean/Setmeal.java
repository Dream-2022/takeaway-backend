package com.example.takeawaybackend.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Setmeal {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private Integer remark_id;
    private String picture;


}
