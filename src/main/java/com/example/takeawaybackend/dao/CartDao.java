package com.example.takeawaybackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.takeawaybackend.bean.Cart;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartDao extends BaseMapper<Cart> {
}
