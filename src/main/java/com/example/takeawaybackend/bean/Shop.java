package com.example.takeawaybackend.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class Shop {
    @TableId(type= IdType.AUTO)
    private Integer id;//商家id，主键
    private Integer userId;
    private String name;//商家名称
    private String addressProvince;
    private String addressCity;
    private String addressCounty;
    @TableField(exist = false)
    private String addressProvinceName;
    @TableField(exist = false)
    private String addressCityName;
    @TableField(exist = false)
    private String addressCountyName;
    private String addressDetail;//商家详细地址
    private String profile;//商家简介
    private String logoPhoto;//门店logo
    private String storePhoto;//门店照
    private String inPhoto;//店内照
    private String background;//背景图片
    @TableField(exist = false)
    private Integer saleNum;//销售量
    @TableField(exist = false)
    private String saleStr;
    private Timestamp createAt;//创建时间(审核通过的时间)
    @TableField(exist = false)
    private Float score;//评分
    private Float begin;//起送价
    private Float delivery;//配送费
    private String type;//商家类型
    @TableField(exist = false)
    private String typeId;
    private Float packing;//打包费
    private String state;//是否成功注册，0管理员未审核，1已注册，2已保存
    private String takeawayCall;//外卖电话
    private String contactCall;//联系电话
    private String realName;//姓名
    @TableField(exist = false)
    private List<Dish> dishList;
}
