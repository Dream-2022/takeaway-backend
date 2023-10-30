package com.example.takeawaybackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.takeawaybackend.bean.Shop;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShopDao extends BaseMapper<Shop> {
}
