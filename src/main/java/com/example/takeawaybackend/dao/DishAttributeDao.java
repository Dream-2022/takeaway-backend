package com.example.takeawaybackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.takeawaybackend.bean.DishAttribute;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishAttributeDao extends BaseMapper<DishAttribute> {
}
