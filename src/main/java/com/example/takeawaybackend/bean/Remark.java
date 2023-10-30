package com.example.takeawaybackend.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Remark {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private Integer order_id;
    private String content;


}
